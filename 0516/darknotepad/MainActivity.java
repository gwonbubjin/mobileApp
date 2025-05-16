package com.example.darknotepad;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout container;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        tvCount = findViewById(R.id.tvCount);

        // + 버튼 클릭 → 메모 추가 화면 이동
        findViewById(R.id.fabAdd).setOnClickListener(v -> {
            startActivity(new Intent(this, NoteEditorActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes() {
        container.removeAllViews();
        List<Note> notes = FileStorageHelper.loadAllNotes(this);
        tvCount.setText("모든 노트 · " + notes.size() + "개");

        for (Note note : notes) {
            View view = LayoutInflater.from(this).inflate(R.layout.note_card, container, false);

            TextView tvTitle = view.findViewById(R.id.tvTitle);
            TextView tvContent = view.findViewById(R.id.tvContent);
            TextView tvDate = view.findViewById(R.id.tvDate);
            ImageButton btnMenu = view.findViewById(R.id.btnMenu);

            tvTitle.setText(note.getTitle());
            tvContent.setText(note.getContent());
            tvDate.setText(note.getDate());

            // 카드 클릭 → 편집
            view.setOnClickListener(v -> {
                Intent intent = new Intent(this, NoteEditorActivity.class);
                intent.putExtra("title", note.getTitle());
                intent.putExtra("content", note.getContent());
                startActivity(intent);
            });

            // 메뉴 버튼 클릭 → 삭제
            btnMenu.setOnClickListener(v -> {
                PopupMenu popup = new PopupMenu(this, v);
                popup.getMenuInflater().inflate(R.menu.note_card_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.menu_delete) {
                        FileStorageHelper.deleteNote(this, note.getTitle());
                        loadNotes(); // 다시 로딩
                        Toast.makeText(this, "노트가 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                });
                popup.show();
            });

            container.addView(view);
        }
    }
}
