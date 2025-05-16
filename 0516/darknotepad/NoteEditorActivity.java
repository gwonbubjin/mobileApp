package com.example.darknotepad;

import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class NoteEditorActivity extends AppCompatActivity {
    EditText etTitle, editNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        etTitle = findViewById(R.id.etTitle);
        editNote = findViewById(R.id.editNote); // 🔧 여기 수정됨

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        if (title != null) etTitle.setText(title);
        if (content != null) editNote.setText(content);

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            FileStorageHelper.saveNote(this,
                    etTitle.getText().toString(),
                    editNote.getText().toString()); // 🔧 여기도 수정
            finish();
        });

        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            FileStorageHelper.deleteNote(this, etTitle.getText().toString());
            finish();
        });
    }
}
