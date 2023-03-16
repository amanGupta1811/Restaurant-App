package com.acpitzone.gulatikirasoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class feedback extends AppCompatActivity {
    Button submit;
    EditText editFeedback;
//    String name, email, mobile;
    String url = "https://gulatikirasoi.com/feedback.php";
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        submit = findViewById(R.id.sub_feedback);
        editFeedback = findViewById(R.id.feedback);
        progressBar = findViewById(R.id.pBarF);

        submit.setOnClickListener((v)->sendFeedback());
    }

    void sendFeedback(){
        String feedbackStr = editFeedback.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");
        String name = sharedPreferences.getString("name","");
        String mobile = sharedPreferences.getString("mobile","");

        boolean validate = isValidate(name,mobile,email,feedbackStr);

        if(!validate){
            return;
        }
        sendFeedbackToDB(name, email, mobile, feedbackStr);
    }

    void sendFeedbackToDB(String nameStr, String emailStr, String mobileStr, String feedback){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                if(response.toString().equals("Thank you for sending us feedback")){

                  //  Toast.makeText(getApplicationContext(),"Check email for otp",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                   editFeedback.setText("");
                    // passTxt.setText("");
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(feedback.this, "Connection Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("name",nameStr);
                map.put("email",emailStr);
                map.put("contact",mobileStr);
                map.put("feedback",feedback);
                // map.put("password",password);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }


    boolean isValidate(String name, String mobile, String email, String feedback){

        if(name.isEmpty()){
            return false;
        } else if (mobile.isEmpty()) {
            return false;
        } else if (email.isEmpty()) {
            return false;
        }
        else if(feedback.isEmpty()){
            editFeedback.setError("Field is empty");
            return false;
        }
        return true;
    }
}