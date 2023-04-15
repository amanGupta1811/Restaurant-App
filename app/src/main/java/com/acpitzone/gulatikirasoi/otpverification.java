package com.acpitzone.gulatikirasoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
public class otpverification extends AppCompatActivity {
    EditText otpV;
    Button verify;
    String url = "https://gulatikirasoi.com/otpVerification.php";
    ProgressBar progressBar;
    String otp;
//    String email = "1";
//    String emailL = "0";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        String email = getIntent().getStringExtra("emailR");
        String name = getIntent().getStringExtra("nameR");
        String mobile = getIntent().getStringExtra("mobileR");

        otpV = findViewById(R.id.otpEdt);
        verify = findViewById(R.id.verify);
        progressBar = findViewById(R.id.pBarE);

        otp = otpV.getText().toString();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(otp.isEmpty()){
//                    verifyOtp(otp,email,mobile,name);
//                    Toast.makeText(getApplicationContext(),"Enter otp",Toast.LENGTH_LONG).show();
//                }
//                else{

//                    verifyOtp(otp,email,mobile,name);
//                }
                verifyOtp();
            }
        });
    }
    void verifyOtp(){
        SharedPreferences sharedPreferences1 = getSharedPreferences("SignInDetails", Context.MODE_PRIVATE);
        String emailS = sharedPreferences1.getString("email", "");
//        String secondSt = sharedPreferences2.getString("second_Str", "");
        SharedPreferences sharedPreferences2= getSharedPreferences("RegisterDetails", Context.MODE_PRIVATE);
        String emailR = sharedPreferences2.getString("email", "");


        String email = getIntent().getStringExtra("emailR");
        String name = getIntent().getStringExtra("nameR");
        String mobile = getIntent().getStringExtra("mobileR");

        String emailL = getIntent().getStringExtra("emailL");
        String nameL = getIntent().getStringExtra("nameL");
        String mobileL = getIntent().getStringExtra("mobileL");
        String otp = otpV.getText().toString();
        if(!otp.isEmpty() && !emailR.isEmpty()){
            verifyOtpDB(otp,email,mobile,name);
        } else if (!otp.isEmpty() && !emailS.isEmpty()) {
            verifyOtpDB(otp,emailL,mobileL,nameL);
        } else{
            Toast.makeText(getApplicationContext(),"Enter otp",Toast.LENGTH_LONG).show();
            //verifyOtp(otp,email,mobile,name);
        }
    }
    void verifyOtpDB(String otpStr, String email,String mobile, String name){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                if(response.equals("Otp verify succesfully")) {
//                    Intent i = new Intent(otpverification.this, cart.class);
//                    startActivity(i);

                    Fragment fragment = new homeFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit();

                    SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", email);
                    editor.putString("name", name);
                    editor.putString("mobile", mobile);
                    editor.apply();
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(otpverification.this, "Connection Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("code",otpStr);
                map.put("email",email);
                // map.put("password",password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}