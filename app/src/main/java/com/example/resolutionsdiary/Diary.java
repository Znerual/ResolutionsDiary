package com.example.resolutionsdiary;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.constraintlayout.widget.Constraints.TAG;

@Entity(tableName = "diary")
@TypeConverters({Converters.class})
public class Diary {
   // @PrimaryKey(autoGenerate = true)
   // public int uid;
    @PrimaryKey
    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "diary")
    public boolean diary;

    @ColumnInfo(name = "reading")
    public boolean reading;

    @ColumnInfo(name = "writing")
    public boolean writing;

    @ColumnInfo(name = "piano")
    public boolean piano;

    @ColumnInfo(name = "youtube")
    public boolean youtube;

    public Diary(Date date, boolean diary, boolean reading, boolean writing, boolean piano, boolean youtube) {
        this.date = date;
        this.diary = diary;
        this.writing = writing;
        this.reading = reading;
        this.youtube = youtube;
        this.piano = piano;
    }

    public void addAttributes(Diary diary) {
        this.diary = diary.diary || this.diary;
        this.reading = diary.reading || this.reading;
        this.writing = diary.writing || this.writing;
        this.piano = diary.piano || this.piano;
        this.youtube = diary.youtube || this.youtube;
        Log.e(TAG, "addAttributes: this:" + this + " diary: " +diary  );
    }
    @NonNull
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM");

        return "Date " + dateFormat.format(date)+  " Diary " + diary+ " Reading " + reading + " Writing " + writing + " Piano " + piano + " Youtube " + youtube;

    }
}
