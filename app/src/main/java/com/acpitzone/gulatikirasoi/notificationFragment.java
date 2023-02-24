package com.acpitzone.gulatikirasoi;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class notificationFragment extends Fragment {

    RecyclerView offerRecycler;
    List<offers> offerList = new ArrayList<>();


    public notificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        offerRecycler = view.findViewById(R.id.notiRecycler);

        offerRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));

        offerAdapter offerAdapter = new offerAdapter(offerList);

        offerList.add(new offers("Flat 60% OFF on first order","FIRST"));
        offerList.add(new offers("40% OFF up to ₹ 120","PERFECT"));
        offerList.add(new offers("40% OFF up to ₹ 120","PERFECT"));
        offerList.add(new offers("40% OFF up to ₹ 120","PERFECT"));
        offerList.add(new offers("40% OFF up to ₹ 120","PERFECT"));
        offerList.add(new offers("40% OFF up to ₹ 120","PERFECT"));
        offerList.add(new offers("40% OFF up to ₹ 120","PERFECT"));

        offerRecycler.setAdapter(offerAdapter);

        return view;
    }
}