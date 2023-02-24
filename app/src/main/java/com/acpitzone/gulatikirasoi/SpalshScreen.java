package com.acpitzone.gulatikirasoi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SpalshScreen extends AppCompatActivity {

    AutoCompleteTextView autocompleteTVD,autocompleteTV;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);

        autocompleteTVD = findViewById(R.id.autoCompleteTextView1);

        String[] city = getResources().getStringArray(R.array.City);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, city);

        autocompleteTV = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        autocompleteTV.setAdapter(arrayAdapter);

        String[] autoCompleteOptions2 = getResources().getStringArray(R.array.Other);
        ArrayAdapter<String> autoCompleteAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteOptions2);
        autocompleteTVD.setAdapter(autoCompleteAdapter2);


        autocompleteTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (autocompleteTV.getText().toString().equals("Delhi")) {
                    String[] cityAry = getResources().getStringArray(R.array.Delhi);
                    ArrayAdapter<String> arrayAdapterD = new ArrayAdapter<>(SpalshScreen.this, android.R.layout.simple_list_item_1, cityAry);
                    autocompleteTVD.setAdapter(arrayAdapterD);
                } else if (autocompleteTV.getText().toString().equals("Others")) {
                    String[] otherAry = getResources().getStringArray(R.array.Other);

                    ArrayAdapter<String> arrayAdapterO = new ArrayAdapter<>(SpalshScreen.this, android.R.layout.simple_list_item_1, otherAry);

                    autocompleteTVD.setAdapter(arrayAdapterO);
                }
            }
        });






    }
    public void register(View view){

        String cityA = autocompleteTV.getText().toString();
        String areaA = autocompleteTVD.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("LocationDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("City", cityA);
        editor.putString("Area", areaA);
        editor.apply();

        Intent i = new Intent(SpalshScreen.this,MainActivity.class);
        startActivity(i);

        finish();
    }


}