package com.example.resolutionsdiary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.DiaryViewHolder> {
    class DiaryViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateItemView;
        private final TextView diaryItemView;
        private final TextView readingItemView;
        private final TextView writingItemView;
        private final TextView pianoItemView;
        private final TextView youtubeItemView;

        private DiaryViewHolder(View itemView) {
            super(itemView);
            diaryItemView = itemView.findViewById(R.id.Itemdiary);
            dateItemView = itemView.findViewById(R.id.Itemdate);
            readingItemView = itemView.findViewById(R.id.Itemreading);
            writingItemView = itemView.findViewById(R.id.Itemwriting);
            pianoItemView = itemView.findViewById(R.id.Itempiano);
            youtubeItemView = itemView.findViewById(R.id.Itemyoutube);
        }

    }

    private final LayoutInflater mInflater;
    private List<Diary> mEntries;
    private Context mContext;

    DiaryListAdapter(Context context) {mInflater = LayoutInflater.from(context); mContext = context;}

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new DiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DiaryViewHolder holder, int position){
        if (mEntries != null) {
            Diary current = mEntries.get(position);
            Log.d(TAG, "onBindViewHolder: current Diary objekt" + current );
            if (mEntries.size() < position)
                Log.e(TAG, "onBindViewHolder: mEntries ist kleienr als die Position" + mEntries.size()+ " < " + position );


                holder.dateItemView.setText(Dates.date_toString(current.date));
                holder.diaryItemView.setText(current.diary ? mContext.getString(R.string.diary) : "");
                holder.readingItemView.setText(current.reading ? mContext.getString(R.string.read_sentence) : "");
                holder.writingItemView.setText(current.writing ? mContext.getString(R.string.write_sentence) : "");
                holder.pianoItemView.setText(current.piano ? mContext.getString(R.string.played_piano) : "");
                holder.youtubeItemView.setText(current.youtube ? mContext.getString(R.string.youtube_time_limit) : "");


        }
        else {

            holder.diaryItemView.setText(mContext.getString(R.string.no_data));
            holder.dateItemView.setText(mContext.getString(R.string.no_data));
            holder.readingItemView.setText(mContext.getString(R.string.no_data));
            holder.writingItemView.setText(mContext.getString(R.string.no_data));
            holder.pianoItemView.setText(mContext.getString(R.string.no_data));
            holder.youtubeItemView.setText(mContext.getString(R.string.no_data));

        }
    }
    void setEntries(List<Diary> diaries) {
        mEntries = diaries;
        notifyDataSetChanged();;
    }

    @Override
    public int getItemCount() {
        if (mEntries != null)
            return mEntries.size();
        return 0;
    }
}
