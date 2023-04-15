package com.acpitzone.gulatikirasoi;

import static com.airpay.airpaysdk_simplifiedotp.utils.Utils.sha256;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.airpay.airpaysdk_simplifiedotp.AirpayActivity;
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
//import com.airpay:airpay:1.1.0;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import com.airpay.airpaysdk_simplifiedotp.AirpayActivity;
import com.airpay.airpaysdk_simplifiedotp.AirpayConfiguration;
import com.airpay.airpaysdk_simplifiedotp.AirpayConfigurationParams;
import com.airpay.airpaysdk_simplifiedotp.Config;
import com.airpay.airpaysdk_simplifiedotp.utils.Transaction;
public class cart extends AppCompatActivity {

    private static final int REQUEST_CODE = 5;
    private static final int REQUEST_CODE_1 = 2;
    private ActivityResultLauncher<Intent> launcher1;
    private ActivityResultLauncher<Intent> launcher2;
    RecyclerView cartRecycler;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    cartAdapter cartAdapter;
    int x = 0;
    //     float gstI = 0;
    List<car> carList = new ArrayList<>();
    TextView sub_total, grand_total, gst, deliveryCh, dis, dis_text, strF, strS, change;
    Button couponBtn, nextBtn;
    int percentAmt, minAmt;
    int y = 0;
    String url = "https://gulatikirasoi.com/charges.php";
    String pay_url = "https://payments.airpay.co.in/pay/directindexapi.php";
    int chargesD;
    float gstD;
    ProgressBar pBar;
    CardView cardView;
    ArrayList<Transaction> transactionList;
    private String ErrorMessage = "invalid";
    public boolean ischaracter;
    public boolean boolIsError_new = true;
//    private ImageView img_down;
//    private LinearLayout layout_address;

    private int k = 0;
    public final int PERMISSION_REQUEST_CODE = 101;
    private String customer_vpa ="";
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
        nextBtn = findViewById(R.id.next_btn);
        cardView = findViewById(R.id.add_Info);
        strF = findViewById(R.id.str_first);
        strS = findViewById(R.id.str_second);
        change = findViewById(R.id.change);
        names = getIntent().getStringArrayListExtra("cartName");
        price = getIntent().getStringArrayListExtra("cartPrice");
        String total = getIntent().getStringExtra("grandT");
        //String check = getIntent().getStringExtra("check");
        //couponBtn.setText(total);

        SharedPreferences sharedPreferences = getSharedPreferences("AddressDetails", Context.MODE_PRIVATE);
        String firstS = sharedPreferences.getString("first_Str", "");
        String secondS = sharedPreferences.getString("second_Str", "");

        SharedPreferences sharedPreferences1 = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String email = sharedPreferences1.getString("email", "");

        if (!firstS.isEmpty() && !secondS.isEmpty() && !email.isEmpty()) {
            cardView.setVisibility(View.VISIBLE);
            strF.setText(firstS);
            strS.setText(secondS);
            nextBtn.setText("Add Payment Method");
        } else {
            cardView.setVisibility(View.GONE);
        }
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!email.isEmpty()) {

                    if (!firstS.isEmpty() && !secondS.isEmpty()) {
                        Intent i = new Intent(getApplicationContext(), paymentMethod.class);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(getApplicationContext(), address.class);
//                        startActivityForResult(i, 2);
                        launcher2.launch(i);
                    }
                    //finish();
                } else {
                    Intent i = new Intent(getApplicationContext(), Register.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Register yourself first", Toast.LENGTH_LONG).show();
//                    finish();
                }
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), address.class);
//                startActivityForResult(i, 2);
                launcher2.launch(i);
            }
        });

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
                Intent in = new Intent(getApplicationContext(), coupon.class);
                in.putExtra("gtotal", String.valueOf(x));
                // i.putExtra("cartList",carList);
                // y = 1;
//                startActivityForResult(in,5);
                launcher1.launch(in);
            }
        });

        launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // handle the result of the second intent here
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        SharedPreferences sharedPreferences2 = getSharedPreferences("AddressDetails", Context.MODE_PRIVATE);
                        String firstSt = sharedPreferences2.getString("first_Str", "");
                        String secondSt = sharedPreferences2.getString("second_Str", "");

                        if (!firstSt.isEmpty() && !secondSt.isEmpty()) {
                            cardView.setVisibility(View.VISIBLE);
                            strF.setText(firstSt);
                            strS.setText(secondSt);
                            nextBtn.setText("Add Payment Method");
                        } else {
                            cardView.setVisibility(View.GONE);
                        }
                    }
                });

        launcher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // handle the result of the first intent her
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String updatedTotalAmount = data.getStringExtra("grandT");
                        String discountTo = data.getStringExtra("discountT");
                        grand_total.setText(updatedTotalAmount);
                        if (!discountTo.equals("0.0")) {
                            dis.setVisibility(View.VISIBLE);
                            dis.setText(discountTo);
                            dis_text.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "You got Rs " + discountTo + " off !", Toast.LENGTH_LONG).show();
                        } else {
                            dis.setVisibility(View.GONE);
                            dis_text.setVisibility(View.GONE);
                            Toast.makeText(this, "Add more items to avail this offer", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == 5 && resultCode == RESULT_OK) {
//            String updatedTotalAmount = data.getStringExtra("grandT");
//            String discountTo = data.getStringExtra("discountT");
//            grand_total.setText(updatedTotalAmount);
//
//            if (!discountTo.equals("0.0")) {
//                dis.setVisibility(View.VISIBLE);
//                dis.setText(discountTo);
//                dis_text.setVisibility(View.VISIBLE);
//                Toast.makeText(this, "You got Rs " + discountTo + " off !", Toast.LENGTH_LONG).show();
//            } else {
//                dis.setVisibility(View.GONE);
//                dis_text.setVisibility(View.GONE);
//                Toast.makeText(this, "Add more items to avail this offer", Toast.LENGTH_LONG).show();
//            }
//        } else if (requestCode == 2 && resultCode == RESULT_OK) {
//            SharedPreferences sharedPreferences = getSharedPreferences("AddressDetails", Context.MODE_PRIVATE);
//            String firstS = sharedPreferences.getString("first_Str", "");
//            String secondS = sharedPreferences.getString("second_Str", "");
//
//            if (!firstS.isEmpty() && !secondS.isEmpty()) {
//                cardView.setVisibility(View.VISIBLE);
//                strF.setText(firstS);
//                strS.setText(secondS);
//                nextBtn.setText("Add Payment Method");
//            } else {
//                cardView.setVisibility(View.GONE);
//            }
//        }
//}


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

                    float gstF = (float) (x * (gstD / 100));
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
                }
                catch (JSONException e) {
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pay(){
        String email = "amangupta181102@gmail.com";
        String phone_et = "9694732706";
        String firstName = "Aman";
        String lastName = "Gupta";
        String add = "aaaaaaa nagar";
        String city = "Alwar";
        String state = "Rajasthan";
        String country = "India";
        String pincode = "301001";
        String orderId ="a123";
        String amount = "11";


        AirpayConfiguration configuration = new AirpayConfiguration(AirpayConfigurationParams.PRODUCTION, AirpayConfigurationParams.AIRPAY_KIT);

        Intent myIntent = new Intent(this, AirpayActivity.class);
        myIntent.putExtra(Config.EXTRA_AIRPAY_CONFIGURATION, configuration);
        Bundle b = new Bundle();

        //Please enter Merchant ID
        b.putString("MERCHANT_ID", "");

        b.putString("EMAIL", email.trim());

        b.putString("PHONE", "" + phone_et.trim());
        b.putString("FIRSTNAME", firstName.trim());
        b.putString("LASTNAME", lastName.trim());
        b.putString("ADDRESS", add.trim());
        b.putString("CITY", city.trim());
        b.putString("STATE", state.trim());
        b.putString("COUNTRY", country.trim());
        b.putString("PIN_CODE", pincode.trim());
        b.putString("ORDER_ID", orderId.trim());
        b.putString("AMOUNT", amount.trim());
        b.putString("CURRENCY", "356"); //Please enter currency code
        b.putString("ISOCURRENCY", "INR"); //Please enter iso-currency value
        b.putString("CHMOD", "");
        b.putString("CUSTOMVAR", "");
        b.putString("TXNSUBTYPE", "");
        b.putString("WALLET", "0");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String sCurDate = df.format(new Date());

        //Please enter - Merchant details
        String sSecret = "";
        String sUserName = "";
        String sPassword = "";

        String sAllData = email.trim()+firstName.trim()
                +lastName.trim()+add.trim()
                +city.trim()+state.trim()
                +country.trim()+amount.trim()
                +orderId.trim()+sCurDate;

        //private key
        String sTemp = sSecret+"@"+sUserName+":|:"+sPassword;
        String sPrivateKey = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            sPrivateKey = sha256(sTemp);
        }

        // key for checksum
        String sTemp2 = sUserName+"~:~"+sPassword;
        String sKey = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            sKey = sha256(sTemp2);
//        }

        // checksum
        sAllData = sKey+"@"+sAllData;
        String sChecksum = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            sChecksum = sha256(sAllData);
//        }
        b.putString("CHECKSUM", sChecksum);
        b.putString("PRIVATEKEY", sPrivateKey);
        b.putString("LANGUAGE", "EN");
        b.putString("SUCCESS_URL", ""); // Please enter success url
        b.putString("FAILURE_URL", ""); // Please enter failure url
        myIntent.putExtras(b);
        startActivityForResult(myIntent, 120);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Bundle bundle = data.getExtras();
            transactionList = new ArrayList<Transaction>();
            transactionList = (ArrayList<Transaction>) bundle.getSerializable("DATA");
            if (transactionList != null) {
                Toast.makeText(this, transactionList.get(0).getSTATUS() + "\n" + transactionList.get(0).getSTATUSMSG(), Toast.LENGTH_LONG).show();

                if (transactionList.get(0).getSTATUS() != null) {
                    Log.e("STATUS -> ", "=" + transactionList.get(0).getSTATUS());

                }
                if (transactionList.get(0).getMERCHANTKEY() != null) {
                    Log.e("MERCHANT KEY -> ", "=" + transactionList.get(0).getMERCHANTKEY());

                }
                if (transactionList.get(0).getMERCHANTPOSTTYPE() != null) {
                    Log.e("MERCHANT POST TYPE ", "=" + transactionList.get(0).getMERCHANTPOSTTYPE());
                }
                if (transactionList.get(0).getSTATUSMSG() != null) {
                    Log.e("STATUS MSG -> ", "=" + transactionList.get(0).getSTATUSMSG()); //  success or fail
                }
                if (transactionList.get(0).getTRANSACTIONAMT() != null) {
                    Log.e("TRANSACTION AMT -> ", "=" + transactionList.get(0).getTRANSACTIONAMT());

                }
                if (transactionList.get(0).getTXN_MODE() != null) {
                    Log.e("TXN MODE -> ", "=" + transactionList.get(0).getTXN_MODE());
                }
                if (transactionList.get(0).getMERCHANTTRANSACTIONID() != null) {
                    Log.e("MERCHANT_TXN_ID -> ", "=" + transactionList.get(0).getMERCHANTTRANSACTIONID()); // order id

                }
                if (transactionList.get(0).getSECUREHASH() != null) {
                    Log.e("SECURE HASH -> ", "=" + transactionList.get(0).getSECUREHASH());
                }
                if (transactionList.get(0).getCUSTOMVAR() != null) {
                    Log.e("CUSTOMVAR -> ", "=" + transactionList.get(0).getCUSTOMVAR());
                }
                if (transactionList.get(0).getTRANSACTIONID() != null) {
                    Log.e("TXN ID -> ", "=" + transactionList.get(0).getTRANSACTIONID());
                }
                if (transactionList.get(0).getTRANSACTIONSTATUS() != null) {
                    Log.e("TXN STATUS -> ", "=" + transactionList.get(0).getTRANSACTIONSTATUS());
                }
                if (transactionList.get(0).getTXN_DATE_TIME() != null) {
                    Log.e("TXN_DATETIME -> ", "=" + transactionList.get(0).getTXN_DATE_TIME());
                }
                if (transactionList.get(0).getTXN_CURRENCY_CODE() != null) {
                    Log.e("TXN_CURRENCY_CODE -> ", "=" + transactionList.get(0).getTXN_CURRENCY_CODE());
                }
                if (transactionList.get(0).getTRANSACTIONVARIANT() != null) {
                    Log.e("TRANSACTIONVARIANT -> ", "=" + transactionList.get(0).getTRANSACTIONVARIANT());
                }
                if (transactionList.get(0).getCHMOD() != null) {
                    Log.e("CHMOD -> ", "=" + transactionList.get(0).getCHMOD());
                }
                if (transactionList.get(0).getBANKNAME() != null) {
                    Log.e("BANKNAME -> ", "=" + transactionList.get(0).getBANKNAME());
                }
                if (transactionList.get(0).getCARDISSUER() != null) {
                    Log.e("CARDISSUER -> ", "=" + transactionList.get(0).getCARDISSUER());
                }
                if (transactionList.get(0).getFULLNAME() != null) {
                    Log.e("FULLNAME -> ", "=" + transactionList.get(0).getFULLNAME());
                }
                if (transactionList.get(0).getEMAIL() != null) {
                    Log.e("EMAIL -> ", "=" + transactionList.get(0).getEMAIL());
                }
                if (transactionList.get(0).getCONTACTNO() != null) {
                    Log.e("CONTACTNO -> ", "=" + transactionList.get(0).getCONTACTNO());
                }
                if (transactionList.get(0).getMERCHANT_NAME() != null) {
                    Log.e("MERCHANT_NAME -> ", "=" + transactionList.get(0).getMERCHANT_NAME());
                }
                if (transactionList.get(0).getSETTLEMENT_DATE() != null) {
                    Log.e("SETTLEMENT_DATE -> ", "=" + transactionList.get(0).getSETTLEMENT_DATE());
                }
                if (transactionList.get(0).getSURCHARGE() != null) {
                    Log.e("SURCHARGE -> ", "=" + transactionList.get(0).getSURCHARGE());
                }
                if (transactionList.get(0).getBILLEDAMOUNT() != null) {
                    Log.e("BILLEDAMOUNT -> ", "=" + transactionList.get(0).getBILLEDAMOUNT());
                }
                if (transactionList.get(0).getISRISK() != null) {
                    Log.e("ISRISK -> ", "=" + transactionList.get(0).getISRISK());
                }

                String transid = transactionList.get(0).getMERCHANTTRANSACTIONID();
                String apTransactionID = transactionList.get(0).getTRANSACTIONID();
                String amount = transactionList.get(0).getTRANSACTIONAMT();
                String transtatus = transactionList.get(0).getTRANSACTIONSTATUS();
                String message = transactionList.get(0).getSTATUSMSG();
                //customer_vpa = transactionList.get(0).getCUSTOMERVPA();
                customer_vpa = "";
                if(transactionList.get(0).getCHMOD().equalsIgnoreCase("upi")){
                    customer_vpa = ":"+transactionList.get(0).getCUSTOMERVPA();
                    Log.e("Verified Hash ==","INSIDE CHMODE UPI CONSIDTION");
                }

                String merchantid = ""; //Please enter Merchant Id
                String username = "";   //Please enter Username
                String sParam = transid + ":" + apTransactionID + ":" + amount + ":" + transtatus + ":" + message + ":" + merchantid + ":" + username+customer_vpa;
                CRC32 crc = new CRC32();
                crc.update(sParam.getBytes());
                String sCRC = "" + crc.getValue();
                Log.e("Verified Hash ==", "sParam= " + sParam);
                Log.e("Verified Hash ==", "Calculate Hash= " + sCRC);
                Log.e("Verified Hash ==", "RESP Secure Hash= " + transactionList.get(0).getSECUREHASH());

                if (sCRC.equalsIgnoreCase(transactionList.get(0).getSECUREHASH())) {
                    Log.e("Airpay Secure ->", " Secure hash matched");
                    Log.e("Verified Hash ==","SECURE HASH MATCHED");
                    //  Toast.makeText(this, " Secure hash matched", Toast.LENGTH_LONG).show();

                } else {
                    Log.e("Airpay Secure ->", " Secure hash mismatched");
                    Log.e("Verified Hash ==","SECURE HASH MIS-MATCHED");
                    //  Toast.makeText(this," Secure hash mismatched" , Toast.LENGTH_LONG).show();

                }

                //Log.e("Remaining Params-->>","Remaining Params-->>"+transactionList.get(0).getMyMap());
                // This code is to get remaining extra value pair.
                for (String key : transactionList.get(0).getMyMap().keySet()) {
                    Log.e("EXTRA-->>", "KEY: " + key + " VALUE: " + transactionList.get(0).getMyMap().get(key));
                    String extra_param= transactionList.get(0).getMyMap().get("PRI_ACC_NO_START"); // To replace key value as you want
                    Log.e("Extra Param -->","="+extra_param);
                    transactionList.get(0).getMyMap().get(key);
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Error Message --- >>>", "Error Message --- >>> " + e.getMessage());
        }
    }
}