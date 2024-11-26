package com.example.appmusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.IMyOnClickListener;
import com.example.appmusic.R;
import com.example.appmusic.activities.PlaySongActivity;
import com.example.appmusic.models.Frame;

import java.util.List;

public class ContentFragForYouAdapter
        extends RecyclerView.Adapter<ContentFragForYouAdapter.ContentViewHolder>
        implements IMyOnClickListener {
    private List<Frame> frameList;
    private Context context;
    private FrameAdapter frameAdapter;

    public ContentFragForYouAdapter(List<Frame> frameList, Context context) {
        this.frameList = frameList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_content,parent,false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        Frame frame = frameList.get(position);

        frameAdapter = new FrameAdapter(context,frame.getListSongs(),frame.getTypeFrame());
            holder.nameFrame.setText(frame.getNameFrame());
        //khởi tạo biến tham chiếu tới interface
        frameAdapter.setMyOnClick(this);
        LinearLayoutManager linear = new LinearLayoutManager(context
                ,LinearLayoutManager.HORIZONTAL,false);

        holder.listSongs.setLayoutManager(linear);
        holder.listSongs.setAdapter(frameAdapter);
    }

    @Override
    public int getItemCount() {
        if(frameList!=null)
            return frameList.size();
        return 0;
    }
    //xử lý khi có sự kiện click vào item bài hát -> chuyển sang activity phát nhạc
    @Override
    public void myOnClick(View view, String imageUrl) {
        Intent intent = new Intent(context, PlaySongActivity.class);
        intent.putExtra("imageUrl",imageUrl);
        context.startActivity(intent);
    }

    public class ContentViewHolder extends RecyclerView.ViewHolder{
        TextView nameFrame;
        RecyclerView listSongs;
        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameFrame=itemView.findViewById(R.id.tvRecently);
            listSongs=itemView.findViewById(R.id.recRecentlySong);
        }
    }
}
