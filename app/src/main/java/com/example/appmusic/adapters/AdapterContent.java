package com.example.appmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;
import com.example.appmusic.models.Frame;

import java.util.List;


public class AdapterContent extends ArrayAdapter<Frame> {

    private Context context;
    private List<Frame> frameList;
    private FrameAdapter frameAdapter;

    public AdapterContent(@NonNull Context context, List<Frame> frameList) {
        super(context, R.layout.item_fragment_content,frameList); //cần truyền tất cả tham số của hàm
        this.context = context;
        this.frameList = frameList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_content
                ,parent,false);

        TextView nameFrame= view.findViewById(R.id.tvRecently);
        RecyclerView listSongs = view.findViewById(R.id.recRecentlySong);

        Frame frame = frameList.get(position);
        nameFrame.setText(frame.getNameFrame());

        LinearLayoutManager linear = new LinearLayoutManager(context
                ,LinearLayoutManager.HORIZONTAL,false);

        frameAdapter = new FrameAdapter(context,frame.getListSongs());
        listSongs.setLayoutManager(linear);
        listSongs.setAdapter(frameAdapter);

        return view;
    }
    public Frame getItem(int position){
        return frameList.get(position);
    }

    @Override
    public int getCount() {
        return frameList.size();
    }
}
