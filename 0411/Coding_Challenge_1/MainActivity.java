// MainActivity.java
package com.example.coding_challenge_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioVersion1, radioVersion2, radioVersion3;
    Button btnDisplay;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        radioVersion1 = findViewById(R.id.radioVersion1);
        radioVersion2 = findViewById(R.id.radioVersion2);
        radioVersion3 = findViewById(R.id.radioVersion3);
        btnDisplay = findViewById(R.id.btnDisplay);
        imageView = findViewById(R.id.imageView);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGroup.getCheckedRadioButtonId();

                if (checkedId == R.id.radioVersion1) {
                    imageView.setImageResource(R.drawable.image0);
                    imageView.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioVersion2) {
                    imageView.setImageResource(R.drawable.image1);
                    imageView.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioVersion3) {
                    imageView.setImageResource(R.drawable.image2);
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.GONE); // 선택 안했을 경우 숨기기
                }
            }
        });

    }
}