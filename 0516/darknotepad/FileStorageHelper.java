package com.example.darknotepad;

import android.content.Context;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileStorageHelper {
    private static final String DIR_NAME = "notes";

    public static void saveNote(Context context, String title, String content) {
        try {
            File dir = new File(context.getFilesDir(), DIR_NAME);
            if (!dir.exists()) dir.mkdir();
            File file = new File(dir, title + ".txt");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<Note> loadAllNotes(Context context) {
        List<Note> notes = new ArrayList<>();
        File dir = new File(context.getFilesDir(), DIR_NAME);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line).append("\n");
                    reader.close();

                    String title = file.getName().replace(".txt", "");
                    String content = sb.toString().trim();
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                            .format(new Date(file.lastModified()));
                    notes.add(new Note(title, content, date));
                } catch (IOException e) { e.printStackTrace(); }
            }
        }
        return notes;
    }

    public static void deleteNote(Context context, String title) {
        File file = new File(context.getFilesDir() + "/" + DIR_NAME, title + ".txt");
        if (file.exists()) file.delete();
    }
}