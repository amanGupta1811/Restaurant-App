package com.acpitzone.gulatikirasoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class cart extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    RecyclerView cartRecycler;
    ArrayList<String> names;
    ArrayList<String> price;
    cartAdapter cartAdapter;
    int x = 0;
//     float gstI = 0;
    List<car> carList = new ArrayList<>();
    TextView sub_total, grand_total, gst, deliveryCh, dis, dis_text;
    Button couponBtn;
    int percentAmt, minAmt;
    int y = 0;
    String url = "https://gulatikirasoi.com/charges.php";
    int chargesD;
    float gstD;
    ProgressBar pBar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecycler = findViewById(R.id.cartRecycler);
        sub_total = findViewById(R.id.subT);
        grand_total = findViewById(R.id.g_total);
        gst = findViewById(R.id.gst);
        couponBtn = findViewById(R.id.coupen_btn);
        deliveryCh = findViewById(R.id.fare);
        pBar = findViewById(R.id.pBarC);
        dis = findViewById(R.id.dis);
        dis_text = findViewById(R.id.dis_text);




        names = getIntent().getStringArrayListExtra("cartName");
        price = getIntent().getStringArrayListExtra("cartPrice");

        String total = getIntent().getStringExtra("grandT");
        //String check = getIntent().getStringExtra("check");
        //couponBtn.setText(total);

        cartRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        for (int i = 0; i < names.size(); i++) {
            carList.add(new car(names.get(i), price.get(i)));
        }
        cartAdapter = new cartAdapter(carList);

        cartRecycler.setAdapter(cartAdapter);



       // float g_totalD;
        for (int j = 0; j < price.size(); j++) {
            x = x + Integer.parseInt(price.get(j));
        }

        fetchData(x);



        couponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(cart.this, coupon.class);
                i.putExtra("gtotal", String.valueOf(x));
                // i.putExtra("cartList",carList);
               // y = 1;
                startActivityForResult(i,5);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String updatedTotalAmount = data.getStringExtra("grandT");
            String discountTo = data.getStringExtra("discountT");
            grand_total.setText(updatedTotalAmount);

            if(!discountTo.equals("0.0")){
                dis.setVisibility(View.VISIBLE);
                dis.setText(discountTo);
                dis_text.setVisibility(View.VISIBLE);
                Toast.makeText(this, "You got Rs "+discountTo+" off !", Toast.LENGTH_LONG).show();
            }
            else{
                dis.setVisibility(View.GONE);
                dis_text.setVisibility(View.GONE);
                Toast.makeText(this, "Add more items to avail this offer", Toast.LENGTH_LONG).show();
            }
       // }
    }

    int fetchData(int x) {
        pBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pBar.setVisibility(View.GONE);
              //  JSONObject jsonObject = null;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("response");

                    JSONObject object = jsonArray.getJSONObject(0);

                    chargesD = Integer.parseInt(object.getString("delivery_charge"));
                    gstD = Float.parseFloat(object.getString("gst_rate"));

                    final DecimalFormat decfor = new DecimalFormat("0.00");

                    float gstF = (float) (x * (gstD/100));
                    float gstI = Float.parseFloat(decfor.format(gstF));
                    float g_total = ((float) (x + gstI + chargesD));


                   float g_totalD = g_total;
//                    Toast.makeText(this, "Add more items to avail this offer 1", Toast.LENGTH_LONG).show();

                    String gstS = Float.toString(gstI);
                    String subS = Float.toString(x);
                    String gTotal = Float.toString(g_totalD);
                    String dCharge = Float.toString(chargesD);

                    sub_total.setText(subS);
                    gst.setText(gstS);
                    grand_total.setText(gTotal);
                    deliveryCh.setText(dCharge);

                   // Toast.makeText(this, "hey", Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pBar.setVisibility(View.GONE);

            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("amount", String.valueOf(x));
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        return chargesD;
    }
}