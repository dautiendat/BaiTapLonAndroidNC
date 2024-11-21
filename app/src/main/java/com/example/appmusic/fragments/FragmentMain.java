package com.example.appmusic.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appmusic.R;
import com.example.appmusic.activities.MainActivity;
import com.example.appmusic.adapters.ViewPagerAdapterScrollBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentMain extends Fragment{

    private TabLayout tabLayoutScrollBar;
    private ViewPagerAdapterScrollBar VPAdapterScrollBar;
    private ViewPager2 viewPagerFragmenMain;
    private TextView hiUser;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHelloUserName();
        viewPagerFragmenMain= view.findViewById(R.id.viewPagerFragmentMain);
        tabLayoutScrollBar= view.findViewById(R.id.tabLayoutScrollBar);
        hiUser = view.findViewById(R.id.hiUser);

        VPAdapterScrollBar = new ViewPagerAdapterScrollBar(getActivity());
        viewPagerFragmenMain.setAdapter(VPAdapterScrollBar);

        new TabLayoutMediator(tabLayoutScrollBar, viewPagerFragmenMain, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText(R.string.bar_foryou);
                    break;
                case 1:
                    tab.setText(R.string.bar_relax);
                    break;
                case 2:
                    tab.setText(R.string.bar_energize);
                    break;
                case 3:
                    tab.setText(R.string.bar_travel);
                    break;
                case 4:
                    tab.setText(R.string.bar_workout);
                    break;
                default:
                    tab.setText(R.string.bar_foryou);
                    break;
            }
        }).attach();


        tabLayoutScrollBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackgroundResource(R.drawable.rounded_background_solid);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundResource(R.drawable.rounded_background);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        TabLayout.Tab firstTab = tabLayoutScrollBar.getTabAt(0);
        firstTab.view.setBackgroundResource(R.drawable.rounded_background_solid);
    }
    private void setHelloUserName(){
        mAuth=FirebaseAuth.getInstance();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        //lấy người dùng hiện tại
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            //lấy id
            String userID = currentUser.getUid();
            //tìm trong collection có document chứa userID
            database.collection("user").document(userID)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            //nếu tìm thấy
                            if(task.isSuccessful()){
                                String userName = task.getResult().getString("userName");
                                hiUser.setText(getString(R.string.hi_username)+" "+userName+",");
                            }else{
                                Toast.makeText(getContext(),task.getException().toString()
                                        ,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }



}
