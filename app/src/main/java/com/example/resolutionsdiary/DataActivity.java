package com.example.resolutionsdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;
import java.util.List;

public class DataActivity extends AppCompatActivity {
    private static final String TAG = "Data";
    private DiaryViewModel mDiaryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final DiaryListAdapter adapter = new DiaryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        mDiaryViewModel = new ViewModelProvider(this).get(DiaryViewModel.class);
        mDiaryViewModel.getmAllEntries().observe(this, new Observer<List<Diary>>() {
            @Override
            public void onChanged(List<Diary> diaries) {
                adapter.setEntries(diaries);
            }
        });


    }

}
