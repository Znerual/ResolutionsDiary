package com.example.resolutionsdiary;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DiaryRepository {

    private DiaryDao mDiaryDao;
    private LiveData<List<Diary>> mAllEntries;
    private LiveData<List<Diary>> mEntriesToday;
    DiaryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDiaryDao = db.diaryDao();
        mAllEntries = mDiaryDao.getAll();
        mEntriesToday = mDiaryDao.findEntriesByDate(Dates.getYesterdayMidnight(), Dates.getTomdayMidnight());
    }

    LiveData<List<Diary>> getmAllEntries() {
        return mAllEntries;
    }
    LiveData<List<Diary>> getmEntriesToday() { return mEntriesToday;}

    void add(Diary diary) {
        Observer<List<Diary>> observerEntriesToday = new Observer<List<Diary>>() {
            @Override
            public void onChanged(List<Diary> diaries) {
                if (diaries != null) {
                    Log.e(TAG, "add: with matching entries"+ diaries.get(0) + " add: " +  diary );
                    diaries.get(0).addAttributes(diary);
                    new updateDiaryAsyncTask(mDiaryDao).execute(diaries.get(0));
                } else {
                    Log.e(TAG, "add: without matching entries"+" add: " +  diary );
                    new insertDiaryAsyncTask(mDiaryDao).execute(diary);
                }
            }
        };

        getmEntriesToday().observeForever(observerEntriesToday);
        getmEntriesToday().removeObserver(observerEntriesToday);


    }
    private void insert(Diary diary) {
        new insertDiaryAsyncTask(mDiaryDao).execute(diary);
    }

    void insertAll(Diary...diaries) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mDiaryDao.insertAll(diaries);
        });
    }

    void update(Diary diary) {
        new updateDiaryAsyncTask(mDiaryDao).execute(diary);
    }
    void delete(Diary diary) {
        new deleteDiaryAsyncTask(mDiaryDao).execute(diary);
    }
    LiveData<List<Diary>> findEntriesByDate(Date from, Date to)  {
        return mDiaryDao.findEntriesByDate(from, to);
    }
    private static class updateDiaryAsyncTask extends AsyncTask<Diary,Void,Void> {
        private DiaryDao mAsyncTaskDao;

        updateDiaryAsyncTask(DiaryDao dao) { mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Diary... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
    private static class insertDiaryAsyncTask extends AsyncTask<Diary,Void,Void> {
        private DiaryDao mAsyncTaskDao;

        insertDiaryAsyncTask(DiaryDao dao) { mAsyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Diary... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
    private static class deleteDiaryAsyncTask extends AsyncTask<Diary, Void, Void> {
        private DiaryDao mAsyncTaskDao;

        deleteDiaryAsyncTask(DiaryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Diary... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}

