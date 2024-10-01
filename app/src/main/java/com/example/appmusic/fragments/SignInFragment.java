package com.example.appmusic.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.appmusic.R;


public class SignInFragment extends Fragment {
    private TextView resetPassword , donHaveAnAccount;
    private FrameLayout frameLayout ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        donHaveAnAccount = view.findViewById(R.id.dont_have_an_account);
        resetPassword = view.findViewById(R.id.reset_password);
        frameLayout = getActivity().findViewById(R.id.register_frame_layout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        donHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignUpFragment());
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new ForgotPasswordFragment());
            }
        });
        }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}