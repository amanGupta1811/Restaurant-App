package com.acpitzone.gulatikirasoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {
    EditText phnTxt,passTxt;
    Button signIn;
    TextView registerS;
    String url = "https://gulatikirasoi.com/login.php";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phnTxt = findViewById(R.id.phnEdt1);
        signIn = findViewById(R.id.signInS);
//        passTxt = findViewById(R.id.passEdt1);
        registerS = findViewById(R.id.registerS);
//        signIn = findViewById(R.id.signInS);

        signIn.setOnClickListener((v)->loginToAcc());
        registerS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignIn.this,Register.class);
                startActivity(i);
                finish();
            }
        });
    }
    void loginToAcc(){
        String phnStr = phnTxt.getText().toString();
      //  String passStr = passTxt.getText().toString();

        boolean isValidate = validateData(phnStr);

        if(!isValidate){
            return;
        }
        loginUserToDatabase(phnStr);
    }
    private void loginUserToDatabase(String phnStr) {
//        Toast.makeText(this, "SignIn successfully", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(SignIn.this,MainActivity.class);
//        startActivity(i);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignIn.this, response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String fetchSuccess = jsonObject.getString("on_success");

                    JSONArray jsonArray = jsonObject.getJSONArray("response");

                    if(fetchSuccess.matches("1")){
                        JSONObject object = jsonArray.getJSONObject(0);

                        String id = object.getString("id");
                        String name = object.getString("name");
                        String email = object.getString("email");
                        String mobile = object.getString("mobile");

                        SharedPreferences sharedPreferences = getSharedPreferences("SignInDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.apply();

                        SharedPreferences sharedPreferences1 = getSharedPreferences("RegisterDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("email", "");
                        editor1.apply();

                        Intent i = new Intent(SignIn.this, otpverification.class);
                        i.putExtra("nameL", name);
                        i.putExtra("emailL", email);
                        i.putExtra("mobileL", mobile);
                        startActivity(i);

                        Toast.makeText(getApplicationContext(),"Check email for otp",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignIn.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("email",phnStr);
                // map.put("password",password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
    boolean validateData(String phnStr){
        if(!Patterns.EMAIL_ADDRESS.matcher(phnStr).matches()){
            phnTxt.setError("Please enter Email Id");
            return false;
        }
//        else if (passStr.trim().length()==0){
//            passTxt.setError("Please enter Password");
//            return false;
//        }
        return true;
    }
}