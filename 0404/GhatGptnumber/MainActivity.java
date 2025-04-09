package com.example.ghatgptnumber;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        int[] operatorIds = {
                R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv, R.id.btnMod
        };

        View.OnClickListener numberClick = v -> {
            Button btn = (Button) v;
            currentInput += btn.getText().toString();
            display.setText(currentInput);
        };

        View.OnClickListener operatorClick = v -> {
            Button btn = (Button) v;
            if (!currentInput.isEmpty()) {
                firstNumber = Double.parseDouble(currentInput);
                operator = btn.getText().toString();
                currentInput = "";
                display.setText(operator);
            }
        };

        for (int id : numberIds)
            findViewById(id).setOnClickListener(numberClick);

        for (int id : operatorIds)
            findViewById(id).setOnClickListener(operatorClick);

        findViewById(R.id.btnC).setOnClickListener(v -> {
            currentInput = "";
            operator = "";
            firstNumber = 0;
            display.setText("0");
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                display.setText(currentInput.isEmpty() ? "0" : currentInput);
            }
        });

        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondNumber = Double.parseDouble(currentInput);
                double result = 0;
                switch (operator) {
                    case "+": result = firstNumber + secondNumber; break;
                    case "-": result = firstNumber - secondNumber; break;
                    case "x": result = firstNumber * secondNumber; break;
                    case "/": result = secondNumber != 0 ? firstNumber / secondNumber : 0; break;
                    case "%": result = firstNumber % secondNumber; break;
                }
                display.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
                operator = "";
            }
        });
    }
}
