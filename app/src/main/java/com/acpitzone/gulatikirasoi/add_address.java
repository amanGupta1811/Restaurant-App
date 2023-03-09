package com.acpitzone.gulatikirasoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class add_address extends AppCompatActivity {

    TextView saveAs, deliveryA, cityA, completeA;
    Button saveA;
    String url = "https://gulatikirasoi.com/addAddress.php";
    ProgressBar pBar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);


        saveAs = findViewById(R.id.saveAs);
        deliveryA = findViewById(R.id.delivereyA);
        cityA = findViewById(R.id.cityA);
        completeA = findViewById(R.id.complteA);
        saveA = findViewById(R.id.btn_save);
        pBar = findViewById(R.id.pBarD);

        saveA.setOnClickListener((v)->addAddress());

    }

    public void addAddress(){

        String saveStr = saveAs.getText().toString();
        String deliveryStr = deliveryA.getText().toString();
        String cityStr = cityA.getText().toString();
        String completeStr = completeA.getText().toString();

        boolean isValidate = validateData(saveStr,deliveryStr,cityStr,completeStr);

        if(!isValidate){
            return;
        }

        addAddressToDB(saveStr,deliveryStr,cityStr,completeStr);
    }

    private void addAddressToDB(String saveStr, String deliveryStr, String cityStr, String completeStr) {
//        Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(address.this,SignIn.class);
//        startActivity(i);

        pBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                pBar.setVisibility(View.GONE);
                if(response.toString().equals("Address add Succesfully")){
                    Intent i = new Intent(add_address.this, address.class);
                    startActivity(i);
                   // Toast.makeText(add_address.this, response.toString(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    saveAs.setText("");
                    deliveryA.setText("");
                    cityA.setText("");
                    completeA.setText("");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(add_address.this, "Connection Error", Toast.LENGTH_SHORT).show();
                pBar.setVisibility(View.GONE);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("saveAs",saveStr);
                map.put("delivery",deliveryStr);
                map.put("city",cityStr);
                map.put("completeAddress",completeStr);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    boolean validateData(String saveStr, String deliveryStr, String cityStr, String completeStr) {

        if (saveStr.isEmpty()) {
            saveAs.setError("Fill this field");
            return false;
        } else if (deliveryStr.isEmpty()) {
            deliveryA.setError("Fill this field");
            return false;
        } else if (cityStr.isEmpty()) {
            cityA.setError("Fill this field");
            return false;
        } else if (completeStr.isEmpty()) {
            completeA.setError("Fill this field");
            return false;
        }
        return true;
    }
}