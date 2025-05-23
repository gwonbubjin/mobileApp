package com.example.todoapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskDialog {

    public interface TaskDialogListener {
        void onTaskAdded(Task task);
    }

    private Context context;
    private TaskDialogListener listener;

    public AddTaskDialog(Context context, TaskDialogListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void show() {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_task, null);
        EditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
        TextView textViewDueDate = dialogView.findViewById(R.id.textViewDueDate);
        Spinner spinnerPriority = dialogView.findViewById(R.id.spinnerPriority);

        final Calendar calendar = Calendar.getInstance();

        textViewDueDate.setOnClickListener(v -> {
            new DatePickerDialog(context, (view, year, month, day) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                new TimePickerDialog(context, (timeView, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                    textViewDueDate.setText("마감일: " + sdf.format(calendar.getTime()));
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        new AlertDialog.Builder(context)
                .setTitle("할 일 추가")
                .setView(dialogView)
                .setPositiveButton("추가", (dialog, which) -> {
                    String title = editTextTitle.getText() != null ? editTextTitle.getText().toString().trim() : "";
                    String dueDate = textViewDueDate.getText() != null ? textViewDueDate.getText().toString().replace("마감일: ", "").trim() : "";
                    String priority = (spinnerPriority.getSelectedItem() != null) ? spinnerPriority.getSelectedItem().toString() : "";

                    if (title.isEmpty() || dueDate.isEmpty()) {
                        Toast.makeText(context, "제목과 마감일을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Task task = new Task(0, title, false, dueDate, priority);
                    if (listener != null) listener.onTaskAdded(task);

                    // 30초 뒤 알람 예약
                    Calendar alarmTime = Calendar.getInstance();
                    alarmTime.add(Calendar.SECOND, 30);

                    Intent intent = new Intent(context, AlarmReceiver.class);
                    intent.putExtra("title", title);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            context,
                            (int) System.currentTimeMillis(),
                            intent,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    if (alarmManager != null) {
                        // Android 12(API 31)+: 정확한 알람 예약 권한 있어야 함
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            if (alarmManager.canScheduleExactAlarms()) {
                                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
                            } else {
                                Toast.makeText(context, "정확한 알람 예약 권한 필요 (앱 설정에서 허용)", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
                        }
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }
}
