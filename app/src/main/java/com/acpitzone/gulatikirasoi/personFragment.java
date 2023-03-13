package com.acpitzone.gulatikirasoi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class personFragment extends Fragment {

    TextView blank;
    Button log_out;

    public personFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_person, container, false);

        blank = view.findViewById(R.id.blank);
        log_out = view.findViewById(R.id.logOut);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                editor1.clear();
                editor1.apply();

                SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("AddressDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                editor2.clear();
                editor2.apply();

                Intent i = new Intent(getActivity(), Register.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        blank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LocationDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(getActivity(), SpalshScreen.class);
                startActivity(i);
                getActivity().finish();

            }
        });

        return view;
    }
}