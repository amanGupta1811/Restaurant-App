package com.acpitzone.gulatikirasoi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Register extends AppCompatActivity {

    Button register;
    EditText nameTxt,mobileTxt,emailTxt,passTxt;
    TextView signInR;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.registerR);
        nameTxt = findViewById(R.id.nameEdt);
        mobileTxt = findViewById(R.id.phnEdt);
        emailTxt = findViewById(R.id.emailEdt);
        passTxt = findViewById(R.id.passEdt);
        signInR = findViewById(R.id.signInR);

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
        String passStr = passTxt.getText().toString();

        boolean isValidate = validateData(nameStr,emailStr,mobileStr,passStr);

        if(!isValidate){
            return;
        }

        registerUserToDB(nameStr,mobileStr,emailStr,passStr);
    }

    private void registerUserToDB(String nameStr, String mobileStr, String emailStr, String passStr) {
        Toast.makeText(this, "Register successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Register.this,SignIn.class);
        startActivity(i);
    }

    boolean validateData(String nameStr,String emailStr, String mobileStr, String passStr){

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

        else if (!isValidPassword(passStr)){
            passTxt.setError("This password is invalid");
            return false;
        }
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