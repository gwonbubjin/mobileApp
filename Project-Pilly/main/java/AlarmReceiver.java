package com.example.pilly;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("ALARM_RECEIVED", "=== 알람 수신 시작 ===");
        String docId = intent.getStringExtra("docId");
        String time = intent.getStringExtra("time");
        String ampm = intent.getStringExtra("ampm");

        Log.d("ALARM_RECEIVED", "onReceive() 호출됨: " + ampm + " " + time + " / docId=" + docId);

        String channelId = "medicine_alarm_channel";
        String channelName = "약 복용 알림";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("약 복용 시간 알림 채널");
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            channel.enableVibration(true);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
                Log.d("ALARM_RECEIVED", "✅ 알림 채널 생성됨");
            } else {
                Log.e("ALARM_RECEIVED", "❌ NotificationManager 가져오기 실패");
            }
        }

        Intent confirmIntent = new Intent(context, ConfirmReceiver.class);
        confirmIntent.putExtra("docId", docId);
        PendingIntent confirmPendingIntent = PendingIntent.getBroadcast(
                context,
                docId.hashCode(),
                confirmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        Log.d("ALARM_RECEIVED", "✅ 확인 PendingIntent 생성됨");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_medicine)
                .setContentTitle("Pilly 약 복용 알림")
                .setContentText(ampm + " " + time + " 복용 약이 있어요!")
                .setAutoCancel(true)
                .addAction(R.drawable.ic_check, "복용 완료", confirmPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(docId.hashCode(), builder.build());
            Log.d("ALARM_RECEIVED", "✅ 알림 표시됨");
        } else {
            Log.e("ALARM_RECEIVED", "❌ NotificationManager 가져오기 실패");
        }

        // ✅ 다음날 알람 재등록
        Log.d("ALARM_RECEIVED", "다음날 알람 재등록 시작");
        AlarmHelper.setAlarm(context, time, ampm, docId);
        Log.d("ALARM_RECEIVED", "=== 알람 수신 완료 ===");
    }
}
