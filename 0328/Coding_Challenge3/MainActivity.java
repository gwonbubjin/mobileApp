package com.example.coding_challenge3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText number1, number2;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        result = findViewById(R.id.result);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide = findViewById(R.id.btnDivide);

        View.OnClickListener listener = v -> {
            double num1, num2;

            try {
                num1 = Double.parseDouble(number1.getText().toString());
                num2 = Double.parseDouble(number2.getText().toString());
            } catch (NumberFormatException e) {
                result.setText("숫자를 제대로 입력하세요");
                return;
            }

            double calcResult = 0;

            int id = v.getId();
            if (id == R.id.btnAdd) {
                calcResult = num1 + num2;
            } else if (id == R.id.btnSubtract) {
                calcResult = num1 - num2;
            } else if (id == R.id.btnMultiply) {
                calcResult = num1 * num2;
            } else if (id == R.id.btnDivide) {
                if (num2 == 0) {
                    result.setText("0으로 나눌 수 없습니다");
                    return;
                }
                calcResult = num1 / num2;
            }

            result.setText(String.valueOf(calcResult));
        };


        btnAdd.setOnClickListener(listener);
        btnSubtract.setOnClickListener(listener);
        btnMultiply.setOnClickListener(listener);
        btnDivide.setOnClickListener(listener);
    }
}
