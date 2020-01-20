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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class DataActivity extends AppCompatActivity {
    private static final String TAG = "Data";
    private int month;
    private DiaryViewModel mDiaryViewModel;
    private DiaryListAdapter adapter;
    private TextView monthTextView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button lastMonthB = findViewById(R.id.lastMonthButton);
        Button nextMonthB = findViewById(R.id.nextMonthButton);
        monthTextView = findViewById(R.id.monthTextView);
        adapter =  new DiaryListAdapter(this);;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        month = Dates.getMonth();
        Log.e(TAG, "onCreate: found month " + month);
        monthTextView.setText(Dates.monthToString(month,this));
        mDiaryViewModel = new ViewModelProvider(this).get(DiaryViewModel.class);
        mDiaryViewModel.getmEntriesMonth(month).observe(this, new Observer<List<Diary>>() {
            @Override
            public void onChanged(List<Diary> diaries) {
                adapter.setEntries(diaries);
                Log.e(TAG, "onChanged: get first data " + diaries.size() );
            }
        });


    }
    public void onPreviousMonthClick(View view) {
        if (month < 1) {
            month = 12;
        } else {
            month -= 1;
        }
    }
    public void onNextMonthClick(View view) {
        if (month < 12) {
            month += 1;
        } else {
            month = 1;
        }
        updateObservers();
    }
    private void updateObservers() {
        mDiaryViewModel.getmEntriesMonth(month).removeObservers(this);
        mDiaryViewModel.getmEntriesMonth(month).observe(this, new Observer<List<Diary>>() {
            @Override
            public void onChanged(List<Diary> diaries) {
                adapter.setEntries(diaries);
                Log.e(TAG, "onChanged: daten sind eingetroffen von Abfrage" + diaries.size());
            }
        });
        Log.e(TAG, "onCreate: found month " + month);
        monthTextView.setText(Dates.monthToString(month,this));
    }
}
