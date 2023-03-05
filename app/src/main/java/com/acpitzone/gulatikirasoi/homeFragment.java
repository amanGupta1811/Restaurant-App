package com.acpitzone.gulatikirasoi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class homeFragment extends Fragment implements SelectedListner {
    RecyclerView homeRecyclerView;
    Toolbar tool;
    TextView next, taxes, rupees, itemT, itemI;
    Button menuB;
    ProgressBar pBar;
    List<item> itemList = new ArrayList<>();
    private List<item> data = new ArrayList<>();
    List<item> cartData = new ArrayList<item>();

    List<String> name = new ArrayList<>();
    List<String> price = new ArrayList<>();

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
        menuB = view.findViewById(R.id.menuB);

//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("LocationDetails", Context.MODE_PRIVATE);
//        String city = sharedPreferences.getString("City", "");
//        String area = sharedPreferences.getString("Area", "");

        //menuB.setText(area);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), cart.class);
                i.putExtra("cartName",(ArrayList<String>) name);
                i.putExtra("cartPrice", (ArrayList<String>) price);
                startActivity(i);
            }
        });

        menuB.setOnClickListener((v)->menu());


        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));



        itemsAdapter = new itemsAdapter(itemList, this);

       fetchData("Menu");




        homeRecyclerView.setAdapter(itemsAdapter);
       // itemsAdapter.upDate(itemList);


        return view;
    }


    private void fetchData(String tag) {
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

                            String tagF = object.getString("tag");

                            menuB.setText(tag);

                            if(tag.equals(tagF)) {
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String price = object.getString("price");
                                String type = object.getString("type");
                                // String video = "https://sdcsupermarket.com/videos/" + object.getString("video");
                                String image = "https://gulatikirasoi.com/menu_images/" + object.getString("image");
                                item menuData = new item(id, name, price, type, image);
                                data.add(menuData);
                            } else if (tag.equals("Menu")) {
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String price = object.getString("price");
                                String type = object.getString("type");
                                // String video = "https://sdcsupermarket.com/videos/" + object.getString("video");
                                String image = "https://gulatikirasoi.com/menu_images/" + object.getString("image");
                                item menuData = new item(id, name, price, type, image);
                                data.add(menuData);
                            }

                        }
                        Collections.shuffle(data);
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
//        price = new ArrayList<>();
//        name = new ArrayList<>();

        if (minMax.equals("+")) {

            String x = itemI.getText().toString();
            String id = item.getId();
            String nameS = item.getName();
            String priceS = item.getPrice();
            String type = item.getType();
            String image = item.getImage();

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
            name.add(nameS);
            price.add(priceS);
        }



        else {
            String x = itemI.getText().toString();
            String nameA = item.getName();
            String priceA = item.getPrice();
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
            name.remove(nameA);
            price.remove(priceA);
        }

    }
    public void menu(){
        // Initializing the popup menu and giving the reference as current context
        PopupMenu popupMenu = new PopupMenu(getContext(), menuB);


        // Inflating popup menu from popup_menu.xml file
        popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // Toast message on menu item clicked
                data.clear();
                fetchData((String) menuItem.getTitle());
                homeRecyclerView.setAdapter(itemsAdapter);
                Toast.makeText(getContext(), ""+menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        // Showing the popup menu
        popupMenu.show();
    }
}





