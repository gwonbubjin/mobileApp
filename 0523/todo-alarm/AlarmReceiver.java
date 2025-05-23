package com.example.todoapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        if (title == null) title = "할 일이 있습니다!";

        Log.d("AlarmReceiver", "onReceive called! 앱 밖에서도 호출됨, title: " + title);

        // 알림 채널 생성 (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "todo_channel",
                    "할 일 알림",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "todo_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("할 일 알림")
                .setContentText("30초 후: " + title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // POST_NOTIFICATIONS 권한 체크 (Android 13+)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            manager.notify((int) System.currentTimeMillis(), builder.build());
        } else {
            Log.e("AlarmReceiver", "알림 권한이 없어 노티를 표시하지 못함");
        }
    }
}
