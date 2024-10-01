package com.example.appmusic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmusic.R;


public class AdapterScrollBarInfor extends RecyclerView.Adapter<AdapterScrollBarInfor.ViewHolder>{

    private Context context;
    private String[] list;
    private itemListener itemListener;
    private int clickedPosition = 0;
    public AdapterScrollBarInfor(Context context, String[] list) {
        this.context = context;
        this.list = list;
    }

    public void setItemClickListener(itemListener itemListener){
        this.itemListener=itemListener;
    }

    public String getItem(int position){
        return list[position];
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_recy_scroll_bar_infor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String str = list[position];
        holder.barInfor.setText(str);
        if(position==clickedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.rounded_background_solid);
        }
        else {
            holder.itemView.setBackgroundResource(R.drawable.rounded_background);
        }
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.length;
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView barInfor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            barInfor=itemView.findViewById(R.id.textBarInfor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int previousPosition = clickedPosition;  // Lưu lại vị trí trước đó
            clickedPosition = getAdapterPosition();  // Cập nhật vị trí được click

            // Cập nhật item trước đó và item hiện tại
            notifyItemChanged(previousPosition);
            notifyItemChanged(clickedPosition);
            itemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface itemListener{
        void onItemClick(int position);
    }
}
