package com.example.eggtimer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    String[] requestPermission = {"android.permission.POST_NOTIFICATIONS"};
    public static int REQUEST_POST_NOTIFICATIONS = 10023;

    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
    int savedMin = 0;
    int savedSec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = findViewById(R.id.edit);
        createNotificationChannel();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, requestPermission, 1000);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "My Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Channel description");
            notificationChannel.setSound(soundUri, null);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Egg Timer")
                .setContentText("계란 삶기가 완료되었습니다.")
                .setContentIntent(pendingIntent)
                .setSound(soundUri)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    public void startTimer(View view) {
        String s = mEditText.getText().toString();
        String[] parts = s.split(":");

        if (parts.length != 2) {
            mEditText.setText("형식: MM:SS");
            return;
        }

        try {
            savedMin = Integer.parseInt(parts[0].trim());
            savedSec = Integer.parseInt(parts[1].trim());
            int totalMillis = (savedMin * 60 + savedSec) * 1000;

            new CountDownTimer(totalMillis, 1000) {
                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000);
                    int minutes = seconds / 60;
                    int sec = seconds % 60;
                    mEditText.setText(String.format("%02d:%02d", minutes, sec));
                }

                public void onFinish() {
                    mEditText.setText("00:00");
                    sendNotification();
                    showExtendDialog();
                }
            }.start();
        } catch (NumberFormatException e) {
            mEditText.setText("숫자만 입력하세요 (예: 07:00)");
        }
    }

    private void showExtendDialog() {
        new AlertDialog.Builder(this)
                .setTitle("타이머 종료")
                .setMessage("타이머를 더 연장하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartSavedTimer();
                    }
                })
                .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void restartSavedTimer() {
        int totalMillis = (savedMin * 60 + savedSec) * 1000;

        new CountDownTimer(totalMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                int sec = seconds % 60;
                mEditText.setText(String.format("%02d:%02d", minutes, sec));
            }

            public void onFinish() {
                mEditText.setText("00:00");
                sendNotification();
                showExtendDialog();
            }
        }.start();
    }
}