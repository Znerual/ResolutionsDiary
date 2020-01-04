package com.example.resolutionsdiary;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;


import androidx.lifecycle.LiveData;

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

    DiaryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDiaryDao = db.diaryDao();
        mAllEntries = mDiaryDao.getAll();
    }

    LiveData<List<Diary>> getmAllEntries() {
        return mAllEntries;
    }
    void add(Diary diary) {
        Calendar calStart = new GregorianCalendar();
        calStart.setTime(diary.date);
        calStart.set(Calendar.DAY_OF_MONTH, calStart.get(Calendar.DAY_OF_MONTH) -1 );
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 1);
        Date midnightYesterday = calStart.getTime();

        Calendar calEnd = new GregorianCalendar();
        calEnd.setTime(diary.date);
        calEnd.set(Calendar.HOUR_OF_DAY, 23);
        calEnd.set(Calendar.MINUTE, 59);
        calEnd.set(Calendar.SECOND, 59);
        calEnd.set(Calendar.MILLISECOND, 999);
        Date midnightTonight = calEnd.getTime();
        List<Diary> matchingEntries = mDiaryDao.findEntriesByDate(midnightYesterday, midnightTonight).getValue();

        DateFormat dateFormat = new SimpleDateFormat("kk:mm - dd.MM");
        Log.e(TAG, "add: dates yesterday" + dateFormat.format(midnightYesterday) + " today " + dateFormat.format(midnightTonight)  );
        if (matchingEntries != null) {
            Log.e(TAG, "add: with matching entries"+ matchingEntries.get(0) + " add: " +  diary );
            matchingEntries.get(0).addAttributes(diary);
            this.update(matchingEntries.get(0));
        } else {
            Log.e(TAG, "add: without matching entries"+" add: " +  diary );
            this.insert(diary);
        }
    }
    private void insert(Diary diary) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mDiaryDao.insert(diary);
        });
    }

    void insertAll(Diary...diaries) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mDiaryDao.insertAll(diaries);
        });
    }

    void update(Diary diary) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mDiaryDao.update(diary);
        });
    }
    void delete(Diary diary) {
        new deleteDiaryAsyncTask(mDiaryDao).execute(diary);
    }
    LiveData<List<Diary>> findEntriesByDate(Date from, Date to)  {
        return mDiaryDao.findEntriesByDate(from, to);
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

