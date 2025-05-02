package com.example.survey;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    PieChart pieChart;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        pieChart = findViewById(R.id.pieChart);
        resultText = findViewById(R.id.resultText);

        int yesCount = 0;
        for (boolean ans : SurveyData.answers) {
            if (ans) yesCount++;
        }

        int total = SurveyData.answers.size();
        float yesPercent = ((float) yesCount / total) * 100;
        float noPercent = 100 - yesPercent;

        String feedback;
        if (yesPercent >= 80) {
            feedback = "훌륭해요! 전반적으로 건강한 생활 습관을 잘 유지하고 있어요.";
        } else if (yesPercent >= 50) {
            feedback = "보통이에요! 몇 가지 습관은 잘 지키고 있지만, 개선할 부분도 있어요.";
        } else {
            feedback = "주의가 필요해요. 건강한 생활 습관을 위해 조금 더 노력해봐요!";
        }

        resultText.setText("✔️ 설문 완료!\n\n"
                + "총 " + total + "개 중 " + yesCount + "개 항목에서 건강한 습관을 유지했어요.\n\n"
                + "📊 평가: " + feedback);

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(yesPercent, "예"));
        entries.add(new PieEntry(noPercent, "아니오"));

        PieDataSet dataSet = new PieDataSet(entries, "설문 결과");
        dataSet.setColors(new int[]{android.graphics.Color.GREEN, android.graphics.Color.RED});
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }
}