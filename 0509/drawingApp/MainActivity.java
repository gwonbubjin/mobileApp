package com.example.drawingapp;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawingView);

        findViewById(R.id.btnColorPicker).setOnClickListener(v -> showColorPickerDialog());
        findViewById(R.id.btnStrokeInput).setOnClickListener(v -> showStrokeInputDialog());
        findViewById(R.id.btnEraser).setOnClickListener(v -> drawingView.setEraserMode(true));
        findViewById(R.id.btnClear).setOnClickListener(v -> drawingView.clear());
        findViewById(R.id.btnUndo).setOnClickListener(v -> drawingView.undo());
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
