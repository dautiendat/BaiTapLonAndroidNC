package com.example.appmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appmusic.R;
import com.example.appmusic.models.Frame;


public class AdapterContent extends ArrayAdapter<Frame> {

    private Context context;
    private Frame[] frameList;


    public AdapterContent(@NonNull Context context, Frame[] frameList) {
        super(context, R.layout.item_fragment_content,frameList); //cần truyền tất cả tham số của hàm
        this.context = context;
        this.frameList = frameList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_content,parent,false);
        TextView nameFrame= view.findViewById(R.id.tvRecently);
        Frame frame = frameList[position];
        nameFrame.setText(frame.getNameFrame());
        return view;
    }
    public Frame getItem(int position){
        return frameList[position];
    }

    @Override
    public int getCount() {
        return frameList.length;
    }
}
