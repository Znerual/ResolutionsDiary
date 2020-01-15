package com.example.resolutionsdiary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
@TypeConverters({Converters.class})
public interface DiaryDao {
    @Query("SELECT * FROM diary ORDER BY date ASC")
    LiveData<List<Diary>> getAll();


    @Query("SELECT * FROM diary WHERE date BETWEEN :from AND :to")
    LiveData<List<Diary>> findEntriesByDate(Date from, Date to);

    @Insert
    void insertAll(Diary... diaries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Diary diary);

    @Update
    void update(Diary diary);

    @Delete
    void delete(Diary diary);

}
