package com.example.todoapp;

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
