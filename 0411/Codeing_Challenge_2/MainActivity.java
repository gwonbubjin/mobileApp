package com.example.coding_challenge_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTask;
    Button btnAdd;
    LinearLayout taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTask = findViewById(R.id.editTask);
        btnAdd = findViewById(R.id.btnAdd);
        taskList = findViewById(R.id.taskList);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    CheckBox checkBox = new CheckBox(MainActivity.this);
                    checkBox.setText(task);
                    taskList.addView(checkBox);
                    editTask.setText("");
                }
            }
        });
    }
}