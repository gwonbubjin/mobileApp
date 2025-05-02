package com.example.gallery;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    int[] images = { R.drawable.sports, R.drawable.radioworld, R.drawable.baseball };
    String[] titles = { "스포츠 뉴스", "RadioWorld", "야구 입문 가이드" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewPager);
        MyPagerAdapter adapter = new MyPagerAdapter(this, images, titles);
        viewPager.setAdapter(adapter);
    }
}
