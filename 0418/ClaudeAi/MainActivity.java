// MainActivity.java
package com.example.claudeai;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mainLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);
        textView = findViewById(R.id.text_view);

        // 텍스트뷰에 컨텍스트 메뉴 등록
        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("배경색 선택");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.red_background) {
            mainLayout.setBackgroundColor(Color.RED);
            Toast.makeText(this, "빨간색 배경으로 변경됨", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.green_background) {
            mainLayout.setBackgroundColor(Color.GREEN);
            Toast.makeText(this, "초록색 배경으로 변경됨", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.blue_background) {
            mainLayout.setBackgroundColor(Color.BLUE);
            Toast.makeText(this, "파란색 배경으로 변경됨", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.yellow_background) {
            mainLayout.setBackgroundColor(Color.YELLOW);
            Toast.makeText(this, "노란색 배경으로 변경됨", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.white_background) {
            mainLayout.setBackgroundColor(Color.WHITE);
            Toast.makeText(this, "흰색 배경으로 변경됨", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}