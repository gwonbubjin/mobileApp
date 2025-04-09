package com.example.imagecontrol;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private float currentRotation = 0f;
    private float currentAlpha = 1.0f;
    private int scaleTypeIndex = 0;


    private ScaleType[] scaleTypes = {
            ScaleType.CENTER,
            ScaleType.CENTER_CROP,
            ScaleType.CENTER_INSIDE,
            ScaleType.FIT_CENTER,
            ScaleType.FIT_XY,
            ScaleType.FIT_START,
            ScaleType.FIT_END
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        Button btnScaleType = findViewById(R.id.btnScaleType);
        Button btnRotate = findViewById(R.id.btnRotate);
        Button btnAlpha = findViewById(R.id.btnAlpha);


        btnScaleType.setOnClickListener(v -> {
            scaleTypeIndex = (scaleTypeIndex + 1) % scaleTypes.length;
            imageView.setScaleType(scaleTypes[scaleTypeIndex]);
        });


        btnRotate.setOnClickListener(v -> {
            currentRotation += 45f;
            imageView.setRotation(currentRotation);
        });

        btnAlpha.setOnClickListener(v -> {
            currentAlpha = (currentAlpha == 1.0f) ? 0.3f : 1.0f;
            imageView.setAlpha(currentAlpha);
        });
    }
}
