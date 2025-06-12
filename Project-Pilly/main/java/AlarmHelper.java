package com.example.pilly;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class AlarmHelper {
    public static void setAlarm(Context context, String time, String ampm, String docId) {
        Log.d("ALARM_SET", "=== 알람 등록 시작 ===");
        Log.d("ALARM_SET", "setAlarm() 호출됨: time=" + time + ", ampm=" + ampm + ", docId=" + docId);

        try {
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            Log.d("ALARM_SET", "분해된 시간 → hour=" + hour + ", minute=" + minute);

            if (ampm.equals("오후") && hour < 12) hour += 12;
            else if (ampm.equals("오전") && hour == 12) hour = 0;
            Log.d("ALARM_SET", "AM/PM 처리 후 → hour=" + hour);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            long triggerTime = calendar.getTimeInMillis();
            if (triggerTime < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                triggerTime = calendar.getTimeInMillis();
            }
            Log.d("ALARM_SET", "설정된 알람 시간: " + calendar.getTime());

            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra("docId", docId);
            intent.putExtra("time", time);
            intent.putExtra("ampm", ampm);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    docId.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );
            Log.d("ALARM_SET", "PendingIntent 생성됨: requestCode=" + docId.hashCode());

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if (alarmManager != null) {
                // Android 12(API 31)+ 정확한 알람 권한 체크
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if (!alarmManager.canScheduleExactAlarms()) {
                        Log.e("ALARM_SET", "⚠ 정확한 알람 권한이 꺼져 있음 → 설정 앱에서 허용해야 작동함");
                        return;
                    }
                }

                // 정확한 알람 등록
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            triggerTime,
                            pendingIntent
                    );
                } else {
                    alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            triggerTime,
                            pendingIntent
                    );
                }

                Log.d("ALARM_SET", "✅ 알람 등록 성공: " + ampm + " " + time);
                Log.d("ALARM_SET", "=== 알람 등록 완료 ===");
            } else {
                Log.e("ALARM_SET", "❌ AlarmManager 가져오기 실패");
            }

        } catch (Exception e) {
            Log.e("ALARM_SET", "❌ 알람 설정 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
