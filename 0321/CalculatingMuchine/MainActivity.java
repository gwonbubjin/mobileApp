package com.example.calculatingmachine;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText edit1, edit2, edit3;

    Button bt1,bt2,bt3,bt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        edit3 = findViewById(R.id.edit3);

        bt1 = findViewById(R.id.button1);
        bt2 = findViewById(R.id.button2);
        bt3 = findViewById(R.id.button3);
        bt4 = findViewById(R.id.button4);

        bt1.setOnClickListener(v -> calculate('+'));
        bt2.setOnClickListener(v -> calculate('-'));
        bt3.setOnClickListener(v -> calculate('*'));
        bt4.setOnClickListener(v -> calculate('/'));
    }

    private void calculate(char operator) {
        try {
            double num1 = Double.parseDouble(edit1.getText().toString());
            double num2 = Double.parseDouble(edit2.getText().toString());
            double result = 0;

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;

                case '-':
                    result = num1 - num2;
                    break;

                case '*':
                    result = num1 * num2;
                    break;

                case '/':
                    if (num2 == 0) {
                        edit3.setText("0으로 나눌수 없습니다.");
                    }
                    result = num1 / num2;
                    break;
            }
            edit3.setText(" "+ result);
        }
        catch (NumberFormatException e) {
            edit3.setText("숫자를 입력하세요!");
        }
    }
}










