package com.example.layout_heavy_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView display;
    String input = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // 숫자 & 점
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9, R.id.btnDot
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(v -> {
                Button b = (Button) v;
                input += b.getText().toString();
                display.setText(input);
            });
        }

        // 연산자
        findViewById(R.id.btnPlus).setOnClickListener(opClick);
        findViewById(R.id.btnMinus).setOnClickListener(opClick);
        findViewById(R.id.btnMultiply).setOnClickListener(opClick);
        findViewById(R.id.btnDivide).setOnClickListener(opClick);

        // 결과 =
        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            try {
                String result = calculateExpression(input);
                display.setText(result);
                input = result;
            } catch (Exception e) {
                display.setText("에러");
                input = "";
            }
        });

        // AC 초기화
        findViewById(R.id.btnAC).setOnClickListener(v -> {
            input = "";
            display.setText("0");
        });

        // < 백스페이스
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (!input.isEmpty()) {
                input = input.substring(0, input.length() - 1);
                display.setText(input.isEmpty() ? "0" : input);
            }
        });
    }

    // 연산 처리 메서드
    private String calculateExpression(String expr) {
        expr = expr.replaceAll("x", "*");

        // 딱 하나의 연산자만 있는 경우만 처리
        if (expr.contains("+")) {
            String[] parts = expr.split("\\+");
            return String.valueOf(Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]));
        } else if (expr.contains("-")) {
            String[] parts = expr.split("-");
            return String.valueOf(Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]));
        } else if (expr.contains("*")) {
            String[] parts = expr.split("\\*");
            return String.valueOf(Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]));
        } else if (expr.contains("/")) {
            String[] parts = expr.split("/");
            double denominator = Double.parseDouble(parts[1]);
            if (denominator == 0) return "0";
            return String.valueOf(Double.parseDouble(parts[0]) / denominator);
        }

        return expr; // 연산자 없으면 그대로 출력
    }

    // 연산자 공통 처리
    View.OnClickListener opClick = v -> {
        Button b = (Button) v;
        // 연산자 중복 입력 방지
        if (!input.isEmpty() && !endsWithOperator(input)) {
            input += b.getText().toString();
            display.setText(input);
        }
    };

    // 연산자 끝에 중복으로 안 붙게 체크
    boolean endsWithOperator(String str) {
        return str.endsWith("+") || str.endsWith("-") || str.endsWith("x") || str.endsWith("/");
    }
}
