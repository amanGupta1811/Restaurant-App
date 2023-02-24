package com.acpitzone.gulatikirasoi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class homeFragment extends Fragment implements SelectedListner {

    RecyclerView homeRecyclerView;
    Toolbar tool;
    TextView next, taxes, rupees, itemT, itemI;
    ProgressBar pBar;
    List<item> itemList = new ArrayList<>();
    private List<item> data = new ArrayList<>();

    String url = "https://gulatikirasoi.com/menu.php";
    private itemsAdapter itemsAdapter;


    public homeFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeRecyclerView = view.findViewById(R.id.homeRecycler);
        tool = view.findViewById(R.id.tool);
        next = view.findViewById(R.id.next);
        itemI = view.findViewById(R.id.item);
        itemT = view.findViewById(R.id.itemT);
        rupees = view.findViewById(R.id.rupees);
        taxes = view.findViewById(R.id.taxes);
        pBar = view.findViewById(R.id.pBar);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), cart.class);
                startActivity(i);
            }
        });

        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));



        itemsAdapter = new itemsAdapter(itemList, this);

//        itemList.add(new item(R.drawable.bg2, "Veg Burger", "80", "veg"));
//        itemList.add(new item(R.drawable.bg4, "Pizza", "280", "veg"));
//        itemList.add(new item(R.drawable.bg2, "Veg Birayani", "180", "veg"));
//        itemList.add(new item(R.drawable.bg4, "Hakka Noodles", "120", "veg"));
//        itemList.add(new item(R.drawable.bg2, "Chole Bhature", "150", "veg"));
//        itemList.add(new item(R.drawable.bg4, "Veg Momos", "80", "veg"));
//        itemList.add(new item(R.drawable.bg2, "Butter Panner Masala Dosa", "150", "veg"));
//        itemList.add(new item(R.drawable.bg2, "Masala Dosa", "110", "veg"));
//        itemList.add(new item(R.drawable.bg2, "Plain Dosa", "80", "veg"));
//        itemList.add(new item(R.drawable.bg4, "Pasata", "130", "veg"));
//        itemList.add(new item(R.drawable.bg2, "Paneer Tikka", "180", "veg"));
//        itemList.add(new item(R.drawable.bg4, "Mushroom Tikka", "380", "veg"));
//        itemList.add(new item(R.drawable.bg2, "Honey chilli potato", "140", "veg"));
//        itemList.add(new item(R.drawable.bg4, "Veg Manchurian", "100", "veg"));
//        itemList.add(new item(R.drawable.bg4, "Aloo Tikki", "60", "veg"));

        fetchData();



        homeRecyclerView.setAdapter(itemsAdapter);
       // itemsAdapter.upDate(itemList);


        return view;

       // itemsAdapter.upDate(itemList);


    }


    private void fetchData() {
        pBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String fetchSuccess = jsonObject.getString("on_success");

                    JSONArray jsonArray = jsonObject.getJSONArray("response");

                    if (fetchSuccess.matches("1")) {

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            String id = object.getString("id");
                            String name = object.getString("name");
                            String price = object.getString("price");
                            String type = object.getString("type");
                           // String video = "https://sdcsupermarket.com/videos/" + object.getString("video");
                            String image = "https://gulatikirasoi.com/menu_images/" + object.getString("image");
                            item menuData = new item(id, name, price,type, image);
                            data.add(menuData);
                        }
                        itemsAdapter.upDate(data);


                    } else {
                       Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            public HashMap<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", "Mozilla/5.0");
                return headers;
            }

            public Priority getPriority() {
                return Priority.IMMEDIATE;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    @Override
    public void onItemClicked(item item, int a, String minMax) {


        if (minMax.equals("+")) {

            String x = itemI.getText().toString();

            if (!(x.equals("item"))) {

                String z = rupees.getText().toString();

                int y = Integer.parseInt(x);
                int b = Integer.parseInt(z);

                int c = Integer.parseInt(item.getPrice());

                tool.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                itemI.setText("" + (y + 1));
                rupees.setText("" + (b + c));
                itemI.setVisibility(View.VISIBLE);
                rupees.setVisibility(View.VISIBLE);
                itemT.setVisibility(View.VISIBLE);
                taxes.setVisibility(View.VISIBLE);

            } else if (x.equals("item")) {
                itemI.setText("" + (a));
                tool.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                rupees.setText(item.getPrice());
                itemI.setVisibility(View.VISIBLE);
                rupees.setVisibility(View.VISIBLE);
                itemT.setVisibility(View.VISIBLE);
                taxes.setVisibility(View.VISIBLE);
            } else {
                tool.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                itemI.setVisibility(View.GONE);
                rupees.setVisibility(View.GONE);
                itemT.setVisibility(View.GONE);
                taxes.setVisibility(View.GONE);
            }

        } else {
            String x = itemI.getText().toString();
            if (x.equals("1") || x.equals("item")) {

                itemI.setText("item");
                tool.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                rupees.setText("rupee");
                itemI.setVisibility(View.GONE);
                rupees.setVisibility(View.GONE);
                itemT.setVisibility(View.GONE);
                taxes.setVisibility(View.GONE);

            } else if (!(x.equals("item")) ) {


                String z = rupees.getText().toString();

                int y = Integer.parseInt(x);
                int b = Integer.parseInt(z);

                int c = Integer.parseInt(item.getPrice());

                tool.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                itemI.setText("" + (y - 1));
                rupees.setText("" + (b - c));
                itemI.setVisibility(View.VISIBLE);
                rupees.setVisibility(View.VISIBLE);
                itemT.setVisibility(View.VISIBLE);
                taxes.setVisibility(View.VISIBLE);



            } else {
                tool.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                itemI.setVisibility(View.GONE);
                rupees.setVisibility(View.GONE);
                itemT.setVisibility(View.GONE);
                taxes.setVisibility(View.GONE);
            }

        }

    }
}





