package com.example.drawingapp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawingView = findViewById(R.id.drawingView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_color) {
            showColorPickerDialog();
            return true;
        } else if (id == R.id.action_stroke) {
            showStrokeInputDialog();
            return true;
        } else if (id == R.id.action_eraser) {
            drawingView.setEraserMode(true);
            return true;
        } else if (id == R.id.action_clear) {
            drawingView.clear();
            return true;
        } else if (id == R.id.action_undo) {
            drawingView.undo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showColorPickerDialog() {
        final int[] colors = {
                Color.BLACK, Color.RED, Color.BLUE,
                Color.GREEN, Color.YELLOW, Color.MAGENTA,
                Color.CYAN, Color.GRAY
        };
        final String[] colorNames = {
                "검정", "빨강", "파랑", "초록", "노랑", "자홍", "청록", "회색"
        };

        new AlertDialog.Builder(this)
                .setTitle("색상 선택")
                .setItems(colorNames, (dialog, which) -> {
                    drawingView.setColor(colors[which]);
                    drawingView.setEraserMode(false);
                })
                .show();
    }

    private void showStrokeInputDialog() {
        EditText input = new EditText(this);
        input.setHint("예: 14");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        new AlertDialog.Builder(this)
                .setTitle("굵기 설정")
                .setView(input)
                .setPositiveButton("설정", (dialog, which) -> {
                    String value = input.getText().toString();
                    if (!value.isEmpty()) {
                        try {
                            float stroke = Float.parseFloat(value);
                            drawingView.setStrokeWidth(stroke);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }
}
