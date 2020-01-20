package com.example.resolutionsdiary;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DiaryRepository {

    private DiaryDao mDiaryDao;
    private LiveData<List<Diary>> mAllEntries;
    private LiveData<List<Diary>> mEntriesToday;
    private LiveData<List<Diary>> mEntriesInMonth;
    private MutableLiveData<List<Diary>> mEntriesTodayMutable = new MutableLiveData<>();
    DiaryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDiaryDao = db.diaryDao();
        mAllEntries = mDiaryDao.getAll();
        mEntriesToday = mDiaryDao.findEntriesByDate(Dates.getYesterdayMidnight(), Dates.getTomdayMidnight());
        mEntriesToday.observeForever(new Observer<List<Diary>>() {
            @Override
            public void onChanged(List<Diary> diaries) {
                mEntriesTodayMutable.setValue(diaries);
            }
        });

    }

    LiveData<List<Diary>> getmAllEntries() {
        return mAllEntries;
    }
    LiveData<List<Diary>> getmEntriesToday() { return mEntriesToday;}
    LiveData<List<Diary>> getmEntriesMonth(int month) {
        mEntriesInMonth = mDiaryDao.findEntriesByDate(Dates.getMonthStart(month),Dates.getMonthEnd(month));
        Log.e(TAG, "getmEntriesMonth: " + Dates.getMonthStart(month) + " end " + Dates.getMonthEnd(month) );
        return mEntriesInMonth;
    }
    void add(Diary diary) {
        List<Diary> entries = mEntriesTodayMutable.getValue();
        if (entries == null) {
            Log.e(TAG, "Entries Null, add: without matching entries"+" add: " +  diary );
            entries.add(diary);

            mEntriesTodayMutable.setValue(entries);
            new insertDiaryAsyncTask(mDiaryDao).execute(diary);
        } else {
                for (Diary entry : entries) {
                    if (Dates.isToday(entry.date)) {
                        Log.e(TAG, "add: with matching entries"+ entry + " add: " +  diary );
                        entry.addAttributes(diary);

                        new updateDiaryAsyncTask(mDiaryDao).execute(entry);
                        return;
                    }
                }
                Log.e(TAG, "No Entry Today, add: without matching entries"+" add: " +  diary );
                entries.add(diary);
                mEntriesTodayMutable.setValue(entries);
                new insertDiaryAsyncTask(mDiaryDao).execute(diary);

        }



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

