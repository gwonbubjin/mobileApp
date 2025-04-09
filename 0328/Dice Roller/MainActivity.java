package com.example.diceroller;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView diceImage;
    int[] diceImages = {
            R.drawable.dice_1,
            R.drawable.dice_2,
            R.drawable.dice_3,
            R.drawable.dice_4,
            R.drawable.dice_5,
            R.drawable.dice_6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diceImage = findViewById(R.id.diceImage);
        Button btnRoll = findViewById(R.id.btnRoll);

        btnRoll.setOnClickListener(v -> {

            RotateAnimation rotate = new RotateAnimation(
                    0f, 360f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f
            );
            rotate.setDuration(500);
            rotate.setFillAfter(true);


            diceImage.startAnimation(rotate);


            new Handler().postDelayed(() -> {
                int randomIndex = new Random().nextInt(6);
                diceImage.setImageResource(diceImages[randomIndex]);
            }, 500); 
        });
    }
}
