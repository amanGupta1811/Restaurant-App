package com.acpitzone.gulatikirasoi;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import java.util.StringJoiner;

public class address extends AppCompatActivity implements addressListner{

    RecyclerView recyclerView;
    Button addA;
    addressAdapter addressAdapter;
    private static final int REQUEST_CODE_1 = 2;
    List<addressData> addressDataList = new ArrayList<>();
    List<addressData> data = new ArrayList<>();
    ProgressBar pBar;
    String url = "https://gulatikirasoi.com/address.php";
    String email;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        recyclerView = findViewById(R.id.addRecycler);
        addA = findViewById(R.id.add_address);
        pBar = findViewById(R.id.pBar7);

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        email = sharedPreferences1.getString("email","");



        addA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(address.this,add_address.class);
                startActivity(i);
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        addressAdapter = new addressAdapter(addressDataList,this);

        fetchData();

        recyclerView.setAdapter(addressAdapter);


    }

    void fetchData(){
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

                            String user_email = object.getString("user_email");
                            if(user_email.equals(email)) {
                                String id = object.getString("id");
                                String saveAs1 = object.getString("saveAs");
                                String delivery1 = object.getString("delivery");
                                String completeAddress1 = object.getString("completeAddress");
                                String city1 = object.getString("city");
                                addressData addressData = new addressData(id, saveAs1, delivery1, completeAddress1, city1);
                                data.add(addressData);
                            }
                        }
                        // Collections.shuffle(data);
                        addressAdapter.upDate(data);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onAddressListner(addressData addressData) {

        String saveAs = addressData.getSaveAl();
        String delivery = addressData.getDeliveryAl();
        String complete = addressData.getCompleteAl();
        String city = addressData.getCityAl();

        StringJoiner s = new StringJoiner(", ");

        String first_Str = "Delivery at " + saveAs;
        s.add(delivery);
        s.add(complete);
        s.add(city);
        String second_Str = s.toString();

        SharedPreferences sharedPreferences = getSharedPreferences("AddressDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_Str", first_Str);
        editor.putString("second_Str", second_Str);
        editor.apply();

        Intent intent = new Intent();
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}