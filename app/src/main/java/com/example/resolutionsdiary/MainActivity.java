package com.example.resolutionsdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String DIARY = "com.example.resolutionsdiary.DIARY";
    public static final String READING = "com.example.resolutionsdiary.READING";
    public static final String WRITING = "com.example.resolutionsdiary.WRITING";
    public static final String PIANO = "com.example.resolutionsdiary.PIANO";
    public static final String YOUTUBE = "com.example.resolutionsdiary.YOUTUBE";
    private DiaryViewModel mDiaryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDiaryViewModel = new ViewModelProvider(this).get(DiaryViewModel.class);


    }
    public void ok_clicked(View view) {

        Intent intent = new Intent(this, DataActivity.class);
        CheckBox diaryCh = (CheckBox) findViewById(R.id.diaryCheckBox);
        CheckBox readingCh = (CheckBox) findViewById(R.id.readingCheckBox);
        CheckBox writingCh = (CheckBox) findViewById(R.id.writingCheckBox);
        CheckBox pianoCh = (CheckBox) findViewById(R.id.pianoCheckBox);
        CheckBox youtubeCh = (CheckBox) findViewById(R.id.youtubeCheckBox);
        Diary addDiary = new Diary(new Date().getTime(), diaryCh.isChecked(), readingCh.isChecked(),writingCh.isChecked(),pianoCh.isChecked(),youtubeCh.isChecked());
        mDiaryViewModel.insert(addDiary);
        startActivity(intent);

    }
    public void history_clicked(View view) {
        Intent intent = new Intent(this, DataActivity.class);
        startActivity(intent);
    }
}
