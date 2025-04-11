package com.example.sand;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CheckBox checkMeat, checkCheese;
    ImageView imageMeat, imageCheese;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkMeat = findViewById(R.id.checkMeat);
        checkCheese = findViewById(R.id.checkCheese);
        imageMeat = findViewById(R.id.imageMeat);
        imageCheese = findViewById(R.id.imageCheese);

        checkMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMeat.isChecked()) {
                    imageMeat.setVisibility(View.VISIBLE);
                } else {
                    imageMeat.setVisibility(View.GONE);
                }
            }
        });

        checkCheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCheese.isChecked()) {
                    imageCheese.setVisibility(View.VISIBLE);
                } else {
                    imageCheese.setVisibility(View.GONE);
                }
            }
        });
    }
}
