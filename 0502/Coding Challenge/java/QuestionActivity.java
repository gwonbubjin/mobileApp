package com.example.survey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {
    int questionIndex = 0;
    TextView questionText;
    Button yesBtn, noBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionText = findViewById(R.id.questionText);
        yesBtn = findViewById(R.id.yesButton);
        noBtn = findViewById(R.id.noButton);

        updateQuestion();

        yesBtn.setOnClickListener(v -> {
            SurveyData.answers.add(true);
            nextQuestion();
        });

        noBtn.setOnClickListener(v -> {
            SurveyData.answers.add(false);
            nextQuestion();
        });
    }

    void updateQuestion() {
        if (questionIndex < SurveyData.questions.length) {
            questionText.setText(SurveyData.questions[questionIndex]);
        }
    }

    void nextQuestion() {
        questionIndex++;
        if (questionIndex < SurveyData.questions.length) {
            updateQuestion();
        } else {
            startActivity(new Intent(this, ResultActivity.class));
            finish();
        }
    }
}
