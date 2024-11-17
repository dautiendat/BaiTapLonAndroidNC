package com.example.appmusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appmusic.R;
import com.example.appmusic.adapters.AdapterContent;
import com.example.appmusic.models.Frame;
import com.example.appmusic.models.ItemSearch;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FragmentRelax extends Fragment {

    private ListView listView;
    private AdapterContent adapter;
    private List<Frame> frameList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relax,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=view.findViewById(R.id.listView_FragRelax);
        initData();
        adapter=new AdapterContent(getActivity(),frameList);
        listView.setAdapter(adapter);
    }

    private void initData() {
//        String[] strings = {"Đã nghe gần đây","Nghệ sĩ bạn theo dõi","Bài hát mới","Danh sách hàng đầu"};
//        List<ItemSearch> list1 = new ArrayList<>();
//        list1.add(new ItemSearch("20 25 30",R.drawable.song));
//        list1.add(new ItemSearch("Một ngày không xa",R.drawable.song));
//        list1.add(new ItemSearch("Không còn",R.drawable.song));
//        List<ItemSearch> list2 = new ArrayList<>();
//        list2.add(new ItemSearch("Hoa nở không màu",R.drawable.song));
//        list2.add(new ItemSearch("Giá như",R.drawable.song));
//        list2.add(new ItemSearch("Không còn",R.drawable.song));
//        List<ItemSearch> list3 = new ArrayList<>();
//        list3.add(new ItemSearch("Tri kỷ",R.drawable.song));
//        list3.add(new ItemSearch("Nắng ấm xa dần",R.drawable.song));
//        list3.add(new ItemSearch("Power",R.drawable.song));
//        List<ItemSearch> list4 = new ArrayList<>();
//        list4.add(new ItemSearch("Galaxy",R.drawable.song));
//        list4.add(new ItemSearch("Mantra",R.drawable.song));
//        list4.add(new ItemSearch("Money",R.drawable.song));
//        List<ItemSearch>[] lists = new List[4]; // Khởi tạo mảng chứa 4 danh sách
//        lists[0] = list1;
//        lists[1] = list2;
//        lists[2] = list3;
//        lists[3] = list4;
//        frames=new Frame[strings.length];
//        for (int i = 0; i < strings.length; i++) {
//            frames[i]=new Frame(strings[i],lists[i]);
//        }

        frameList=new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("FrameList");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                List<ItemSearch> list = snapshot.getValue(Frame.class).getListSongs();
                Frame frame = snapshot.getValue(Frame.class);
                if(frame != null) {
                    frame.setListSongs(list);
                    frameList.add(frame);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
