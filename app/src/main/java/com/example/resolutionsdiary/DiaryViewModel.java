package com.example.resolutionsdiary;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DiaryViewModel extends AndroidViewModel {
    private DiaryRepository mRepositroy;

    private LiveData<List<Diary>> mAllEntries;

    public DiaryViewModel(Application application) {
        super(application);
        mRepositroy = new DiaryRepository(application);
        mAllEntries =mRepositroy.getmAllEntries();
    }

    LiveData<List<Diary>> getmAllEntries() { return mAllEntries;}

    public void insert(Diary diary) {mRepositroy.add(diary);}
}
