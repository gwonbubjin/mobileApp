package com.example.todoapp;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        TaskAdapter.OnTaskChangeListener,
        AddTaskDialog.TaskDialogListener {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private TaskDatabaseHelper dbHelper;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ✅ Android 13+ 알림 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        1001
                );
            }
        }

        dbHelper = new TaskDatabaseHelper(this);
        taskList = dbHelper.getAllTasks();

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new TaskAdapter(taskList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            AddTaskDialog dialog = new AddTaskDialog(this, this);
            dialog.show();
        });

        // ✅ (선택) 알림 채널 생성 코드 (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel = new android.app.NotificationChannel(
                    "todo_channel",
                    "할 일 알림",
                    android.app.NotificationManager.IMPORTANCE_HIGH
            );
            android.app.NotificationManager manager =
                    getSystemService(android.app.NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    // 다이얼로그에서 새 할 일 추가 시 콜백
    @Override
    public void onTaskAdded(Task task) {
        dbHelper.insertTask(task);
        taskList = dbHelper.getAllTasks();
        adapter.updateList(taskList);
    }

    @Override
    public void onTaskChecked(Task task, boolean isChecked) {
        task.setDone(isChecked);
        dbHelper.updateTask(task);
    }

    @Override
    public void onTaskDeleted(Task task) {
        dbHelper.deleteTask(task.getId());
        taskList = dbHelper.getAllTasks();
        adapter.updateList(taskList);
    }
}
