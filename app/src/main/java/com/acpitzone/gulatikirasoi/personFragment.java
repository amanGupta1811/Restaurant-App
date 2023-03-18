package com.acpitzone.gulatikirasoi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class personFragment extends Fragment {

    TextView blank;
    Button log_out, address, menu, offer, feedback, aboutUs;
    String userName,userMobile,userEmail,userAddress;
    TextView user_name, user_mobile, user_email, user_address, user_address1;

    public personFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_person, container, false);

      //  blank = view.findViewById(R.id.blank);
        log_out = view.findViewById(R.id.logOut);
        user_name = view.findViewById(R.id.user_Name);
        user_mobile = view.findViewById(R.id.user_Mobile);
        user_address = view.findViewById(R.id.user_add);
        user_email = view.findViewById(R.id.user_email);
        address = view.findViewById(R.id.btn1);
        offer = view.findViewById(R.id.btn2);
        menu = view.findViewById(R.id.btn3);
        user_address1 = view.findViewById(R.id.user_add1);
        feedback = view.findViewById(R.id.btn4);
        aboutUs = view.findViewById(R.id.btn5);



        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("AddressDetails", Context.MODE_PRIVATE);
        String firstS = sharedPreferences.getString("first_Str", "");
        String secondS = sharedPreferences.getString("second_Str", "");


        SharedPreferences sharedPreferences1 = this.getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
         userEmail = sharedPreferences1.getString("email","");
         userName = sharedPreferences1.getString("name","");
         userMobile = sharedPreferences1.getString("mobile","");

         if(!userName.isEmpty()){

             user_name.setText(userName);
             user_mobile.setText(userMobile);
             user_email.setText(userEmail);
             user_address.setText(firstS);
             user_address1.setText(secondS);
         }
         else{
             user_name.setText("GUEST USER");
         }


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

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), com.acpitzone.gulatikirasoi.address.class);
                startActivity(i);
            }
        });

        offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment offerF = new notificationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flFragment, offerF);
                transaction.addToBackStack(null);
                transaction.commit();
               // getActivity().finish();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment menuF = new homeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flFragment, menuF);
                transaction.addToBackStack(null);
                transaction.commit();
               // getActivity().finish();
//                Intent i =new Intent(getActivity(),paymentMethod.class);
//                startActivity(i);
                //getActivity().finish();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), com.acpitzone.gulatikirasoi.feedback.class);
                startActivity(i);
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), com.acpitzone.gulatikirasoi.aboutUs.class);
                startActivity(i);
            }
        });

//        blank.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LocationDetails", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.clear();
//                editor.apply();
//                Intent i = new Intent(getActivity(), SpalshScreen.class);
//                startActivity(i);
//                getActivity().finish();
//
//            }
//        });

        return view;
    }
}