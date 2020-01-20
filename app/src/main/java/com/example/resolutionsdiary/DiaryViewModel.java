package com.example.resolutionsdiary;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {
    private DiaryRepository mRepositroy;

    private LiveData<List<Diary>> mAllEntries;
    private LiveData<List<Diary>> mEntriesThisMonth;
    public DiaryViewModel(Application application) {
        super(application);
        mRepositroy = new DiaryRepository(application);
        mAllEntries =mRepositroy.getmAllEntries();
        mEntriesThisMonth =mRepositroy.getmEntriesMonth(Dates.getMonth());
    }

    LiveData<List<Diary>> getmAllEntries() { return mAllEntries;}
    LiveData<List<Diary>> getmEntriesMonth(int month) {
        mEntriesThisMonth = mRepositroy.getmEntriesMonth(month);
        return mEntriesThisMonth;
    }
    public void insert(Diary diary) {mRepositroy.add(diary);}
}
