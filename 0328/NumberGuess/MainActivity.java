package com.example.numberguess;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int answer; 
    private Random random;
    private TextView resultText;
    private EditText inputGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      
        inputGuess = findViewById(R.id.inputGuess);
        Button btnGuess = findViewById(R.id.btnGuess);
        resultText = findViewById(R.id.resultText);
        ImageView bannerImage = findViewById(R.id.bannerImage);

        
        random = new Random();
        answer = random.nextInt(100) + 1; // 1~100

   
        bannerImage.setImageResource(R.drawable.guess); 

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = inputGuess.getText().toString();

                if (input.isEmpty()) {
                    resultText.setText("숫자를 입력하세요!");
                    return;
                }

                int guess = Integer.parseInt(input);

                if (guess < answer) {
                    resultText.setText("낮아!!");
                } else if (guess > answer) {
                    resultText.setText("높아!!");
                } else {
                    resultText.setText("정답임 ㅋ!! 🎉");
                }
            }
        });
    }
}
