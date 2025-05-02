package com.example.simpleitem2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);

        ArrayList<HashMap<String, String>> dataList = new ArrayList<>();

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("title", "홍길동");
        map1.put("subtitle", "010-1234-5678");
        dataList.add(map1);

        HashMap<String, String> map2 = new HashMap<>();
        map2.put("title", "임꺽정");
        map2.put("subtitle", "010-2345-6789");
        dataList.add(map2);

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                dataList,
                android.R.layout.simple_list_item_2,
                new String[]{"title", "subtitle"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        listView.setAdapter(adapter);
    }
}
