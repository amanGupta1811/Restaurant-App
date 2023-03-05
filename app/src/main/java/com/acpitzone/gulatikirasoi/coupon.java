package com.acpitzone.gulatikirasoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class coupon extends AppCompatActivity implements CouponListner {
    ProgressBar pBar;
    RecyclerView couponRecycler;
    coupenAdapter coupenAdapter;
    List<coupen> couponList = new ArrayList<>();
    List<coupen> data = new ArrayList<>();
//    String total;
//    float g_total;

    String url = "https://gulatikirasoi.com/offer.php";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);



        pBar = findViewById(R.id.pBar2);
        couponRecycler = findViewById(R.id.coupenRecycler);

        couponRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        coupenAdapter = new coupenAdapter(couponList,this);

        fetchData();

        couponRecycler.setAdapter(coupenAdapter);

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
                            String offerN = object.getString("offer_name");
                            String offerC = object.getString("offer_code");
                            String offerP = object.getString("offer_percent");
                            String offerM = object.getString("offer_min_amt");
                            coupen coupen = new coupen(id, offerN, offerC, offerP, offerM);
                            data.add(coupen);
                        }
                        // Collections.shuffle(data);
                        coupenAdapter.upDate(data);
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

    @Override
    public void onCouponClick(coupen coupen) {
        String percent = coupen.getOfferPercent1();
        String min = coupen.getOfferMinAmt1();
        float g_totalD;
        float discount;

        String total = getIntent().getStringExtra("gtotal");
        float g_total = Float.parseFloat(total);

        float percentAmt = Float.parseFloat(percent);
        float minAmt = Float.parseFloat(min);

        if(g_total>=minAmt){
            discount = g_total*(percentAmt/100);
            g_totalD = g_total - discount;
        }
        else{
            discount = 0;
            g_totalD = g_total;
        }

        String gTotalD = String.valueOf(g_totalD);

//        Intent i = new Intent(coupon.this,cart.class);
//        //i.putExtra("percentAmt",percentAmt);
//        //i.putExtra("minAmt",minAmt);
//        i.putExtra("grandT",gTotalD);
//      //  i.putExtra("check","10");
//        startActivity(i);

        Intent intent = new Intent();
        intent.putExtra("grandT", gTotalD);
        intent.putExtra("discountT", String.valueOf(discount));
        setResult(5,intent);
        finish();

    }
}