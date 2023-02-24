package com.acpitzone.gulatikirasoi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    EditText phnTxt,passTxt;
    Button signIn;
    TextView registerS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        phnTxt = findViewById(R.id.phnEdt1);
        passTxt = findViewById(R.id.passEdt1);
        registerS = findViewById(R.id.registerS);
        signIn = findViewById(R.id.signInS);

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
        String passStr = passTxt.getText().toString();

        boolean isValidate = validateData(phnStr,passStr);

        if(!isValidate){
            return;
        }

        loginUserToDatabase(phnStr, passStr);

    }

    private void loginUserToDatabase(String phnStr, String passStr) {
        Toast.makeText(this, "SignIn successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(SignIn.this,MainActivity.class);
        startActivity(i);

    }

    boolean validateData(String phnStr, String passStr){

        if(phnStr.trim().length()==0){
            phnTxt.setError("Please enter Email Id");
            return false;
        }
        else if (passStr.trim().length()==0){
            passTxt.setError("Please enter Password");
            return false;
        }
        return true;
    }


}