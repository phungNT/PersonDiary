package com.example.persondiary;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {
    private Context context;
    private ArrayList<Diary> diaries;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public  DiaryAdapter(Context context, ArrayList<Diary> diaries){
        this.context = context;
        this.diaries = diaries;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.diary_item, parent, false);
        return new DiaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        Diary currentDiary = diaries.get(position);
        String dateTime  = currentDiary.getDateTime();
        String title = currentDiary.getTitle();
        holder.txtDateTime.setText(dateTime);
        holder.txtTitle.setText(title);

    }

    @Override
    public int getItemCount() {
        return diaries.size();
    }


    public  class DiaryViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDateTime;
        public TextView txtTitle;
        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
 }
