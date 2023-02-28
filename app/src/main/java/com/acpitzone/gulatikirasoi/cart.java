package com.acpitzone.gulatikirasoi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class cart extends AppCompatActivity {

    RecyclerView cartRecycler;
    ArrayList<String> names;
    ArrayList<String> price;

    cartAdapter cartAdapter;
    int x =0 ;
   // float gstI = 0;

    List<car> carList = new ArrayList<>();
    TextView sub_total,grand_total,gst;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecycler = findViewById(R.id.cartRecycler);
        sub_total = findViewById(R.id.subT);
        grand_total = findViewById(R.id.g_total);
        gst = findViewById(R.id.gst);

        names = getIntent().getStringArrayListExtra("cartName");
        price = getIntent().getStringArrayListExtra("cartPrice");

        cartRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        for(int i = 0 ; i<names.size(); i++) {
            carList.add(new car(names.get(i),price.get(i)));
        }
        cartAdapter = new cartAdapter(carList);

        cartRecycler.setAdapter(cartAdapter);

        for(int j = 0; j<price.size(); j++){
            x = x + Integer.parseInt(price.get(j));
        }

        float gstI = (float) (x*0.05);
        float g_total = (float) (x + gstI);

        String gstS = Float.toString(gstI);
        String subS = Float.toString(x);
        String gTotal = Float.toString(g_total);

        sub_total.setText(subS);
        gst.setText(gstS);
        grand_total.setText(gTotal);


    }
}