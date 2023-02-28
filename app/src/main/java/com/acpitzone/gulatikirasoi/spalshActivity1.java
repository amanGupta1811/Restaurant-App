package com.acpitzone.gulatikirasoi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class spalshActivity1 extends AppCompatActivity {

    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh1);

        progressBar = findViewById(R.id.progressB);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("LocationDetails", Context.MODE_PRIVATE);
                String city = sharedPreferences.getString("City", "");
                String area = sharedPreferences.getString("Area", "");

                Intent i;
                if(!city.isEmpty()&&!area.isEmpty()){
                    i = new Intent(spalshActivity1.this, MainActivity.class);

                }
                else{
                    i = new Intent(spalshActivity1.this, SpalshScreen.class);
                }
                startActivity(i);
                finish();
                progressBar.setVisibility(View.GONE);
            }
        },2000);

    }
}