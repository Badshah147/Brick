package com.bricks_klin.brickmanagment;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bricks_klin.Util.AppConstant;
import com.bricks_klin.Util.SetterGetter;
import com.bricks_klin.Util.SetterGetterP;
import com.bricks_klin.Util.SetterGetterSales;
import com.bricks_klin.adapter.CustomGrid;
import com.bricks_klin.adapter.CustomGridPurchase;
import com.bricks_klin.adapter.CustomGridSales;
import com.bricks_klin.prefrences.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Purchases extends AppCompatActivity {

    ListView listView;
    ArrayList<SetterGetterP> arrayList;
    CustomGridPurchase customGrid;
    String url="getpurchases.php";
    String url2="searchpurchases.php";
    PrefManager prefManager;
    EditText edtsearch;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);
        listView=(ListView)findViewById(R.id.lvpurchases);
        edtsearch=(EditText)findViewById(R.id.edtpurchasesearch);
        arrayList=new ArrayList<SetterGetterP>();
        prefManager=new PrefManager(this);
        getpurchases(prefManager.getBhatta());

        setedtsdate();
        fdateclick();
        edtsearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (edtsearch.getRight() - edtsearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here

                        searchPurchase(prefManager.getBhatta(),edtsearch.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });

    }
    private void getpurchases(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(Purchases.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            arrayList=new ArrayList<SetterGetterP>();
                            JSONArray jArrayObj= new JSONArray (response);
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String Id = person.getString("Id");
                                String raw_mat_name = person.getString("raw_mat_name");
                                String p_date= person.getString("p_date");
                                String p_price = person.getString("p_price");
                                String p_current_qty = person.getString("p_current_qty");
                                String klinid = person.getString("klinid");
                                String p_by = person.getString("p_by");
                                SetterGetterP setterGetterP=new SetterGetterP();
                                setterGetterP.setId(Id);
                                setterGetterP.setRawmat(raw_mat_name);
                                setterGetterP.setDate(p_date);
                                setterGetterP.setPrice(p_price);
                                setterGetterP.setQty(p_current_qty);
                                setterGetterP.setKlinid(klinid);
                                setterGetterP.setPby(p_by);
                                arrayList.add(setterGetterP);
                            }
                            customGrid=new CustomGridPurchase(arrayList,Purchases.this);
                            listView.setAdapter(customGrid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                Log.e("JsonResponse",""+"Error timeout");
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("klin_name", mobile);

                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }
    private void setedtsdate() {
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate(count);
            }

        };

        // onclick - popup datepicker


    }
    private void fdateclick() {
        edtsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 1;
                new DatePickerDialog(Purchases.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateDate(int c) {

        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        edtsearch.setText(sdf.format(myCalendar.getTime()));
        Log.e("DateSettingTwo", edtsearch.getText().toString());

    }
    private void searchPurchase(final String mobile,final String datee){
        RequestQueue queue = Volley.newRequestQueue(Purchases.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            arrayList=new ArrayList<SetterGetterP>();
                            JSONArray jArrayObj= new JSONArray (response);
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String Id = person.getString("Id");
                                String raw_mat_name = person.getString("raw_mat_name");
                                String p_date= person.getString("p_date");
                                String p_price = person.getString("p_price");
                                String p_current_qty = person.getString("p_current_qty");
                                String klinid = person.getString("klinid");
                                String p_by = person.getString("p_by");
                                SetterGetterP setterGetterP=new SetterGetterP();
                                setterGetterP.setId(Id);
                                setterGetterP.setRawmat(raw_mat_name);
                                setterGetterP.setDate(p_date);
                                setterGetterP.setPrice(p_price);
                                setterGetterP.setQty(p_current_qty);
                                setterGetterP.setKlinid(klinid);
                                setterGetterP.setPby(p_by);
                                arrayList.add(setterGetterP);
                            }
                            customGrid=new CustomGridPurchase(arrayList,Purchases.this);
                            listView.setAdapter(customGrid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                Log.e("JsonResponse",""+"Error timeout");
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("klin_name", mobile);
                params.put("p_date", datee);
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }
}
