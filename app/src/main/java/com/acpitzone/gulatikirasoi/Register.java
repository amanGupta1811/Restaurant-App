package com.acpitzone.gulatikirasoi;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Register extends AppCompatActivity {
    Button register;
    EditText nameTxt,mobileTxt,emailTxt,passTxt;
    TextView signInR;
    ProgressBar progressBar;
    String url = "https://gulatikirasoi.com/emailVerification.php";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.registerR);
        nameTxt = findViewById(R.id.nameEdt);
        mobileTxt = findViewById(R.id.phnEdt);
        emailTxt = findViewById(R.id.emailEdt);
        //passTxt = findViewById(R.id.passEdt);
        signInR = findViewById(R.id.signInR);
        progressBar = findViewById(R.id.pBarD);

        register.setOnClickListener((v)->signUpToAcc());
        signInR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,SignIn.class);
                startActivity(i);
                finish();
            }
        });
    }
    public void signUpToAcc(){

        String nameStr = nameTxt.getText().toString();
        String mobileStr = mobileTxt.getText().toString();
        String emailStr = emailTxt.getText().toString();
       // String passStr = passTxt.getText().toString();

        boolean isValidate = validateData(nameStr,emailStr,mobileStr);
        if(!isValidate){
            return;
        }
        registerUserToDB(nameStr,mobileStr,emailStr);
    }
    private void registerUserToDB(String nameStr, String mobileStr, String emailStr) {
//        Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
//        Intent i = new Intent(Register.this,SignIn.class);
//        startActivity(i);

        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                if(response.toString().equals("SignUp Succesfully")){
                    Intent i = new Intent(Register.this, otpverification.class);
                    i.putExtra("emailR",emailStr);
                    i.putExtra("nameR",nameStr);
                    i.putExtra("mobileR",mobileStr);
                    startActivity(i);
//                    SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("email", emailStr);
//                    editor.putString("name", nameStr);
//                    editor.putString("mobile",mobileStr);
//                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Check email for otp",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    nameTxt.setText("");
                    emailTxt.setText("");
                    mobileTxt.setText("");
                   // passTxt.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, "Connection Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("name",nameStr);
                map.put("email",emailStr);
                map.put("mobile",mobileStr);
               // map.put("password",password);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }




    boolean validateData(String nameStr,String emailStr, String mobileStr){

        if(nameStr.isEmpty()){
            nameTxt.setError("Enter Name");
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            emailTxt.setError("Email is invalid");
            return false;
        }
        else if(mobileStr.length()!=10){
            mobileTxt.setError("Phone no. is invalid");
            return false;
        }
//
//        else if (!isValidPassword(passStr)){
//            passTxt.setError("This password is invalid");
//            return false;
//        }
        return true;
    }
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
}