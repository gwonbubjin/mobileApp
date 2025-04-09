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

    
        findViewById(R.id.btnPlus).setOnClickListener(opClick);
        findViewById(R.id.btnMinus).setOnClickListener(opClick);
        findViewById(R.id.btnMultiply).setOnClickListener(opClick);
        findViewById(R.id.btnDivide).setOnClickListener(opClick);

        
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


        findViewById(R.id.btnAC).setOnClickListener(v -> {
            input = "";
            display.setText("0");
        });

     
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (!input.isEmpty()) {
                input = input.substring(0, input.length() - 1);
                display.setText(input.isEmpty() ? "0" : input);
            }
        });
    }


    private String calculateExpression(String expr) {
        expr = expr.replaceAll("x", "*");

    
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

        return expr; 
    }


    View.OnClickListener opClick = v -> {
        Button b = (Button) v;
      
        if (!input.isEmpty() && !endsWithOperator(input)) {
            input += b.getText().toString();
            display.setText(input);
        }
    };

  
    boolean endsWithOperator(String str) {
        return str.endsWith("+") || str.endsWith("-") || str.endsWith("x") || str.endsWith("/");
    }
}
