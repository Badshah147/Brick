package com.bricks_klin.brickmanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.bricks_klin.prefrences.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPurchases extends AppCompatActivity {

    Spinner spinner;
    EditText edtname;
    PrefManager prefManager;
    String url="getKlins.php";
    List<String> mylist;
    String klinname;

    String url2="getraw.php";
    List<String> mylist2;
    String rawname;
    EditText edtdate,edtprice,edtpurchasedby,edtqty;
    Button btnadd;
    String url3="addpurchase.php";
    String todate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchases);
        edtname=(EditText)findViewById(R.id.spinnerraw1);
        spinner=(Spinner)findViewById(R.id.spinnerklin);
        edtdate=(EditText)findViewById(R.id.edtdatee);
        edtprice=(EditText)findViewById(R.id.edtprice);
        edtpurchasedby=(EditText)findViewById(R.id.edtpurchasedby);
        edtqty=(EditText)findViewById(R.id.edtpurchaseqty);
        btnadd=(Button)findViewById(R.id.btnaddpurchase);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        todate=dateFormat.format(date);
        edtdate.setText(todate);
        prefManager=new PrefManager(this);
        getklins(prefManager.getcell());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                klinname=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        getraw();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpurchase(edtname.getText().toString(),todate,edtprice.getText().toString(),edtqty.getText().toString(),klinname,edtpurchasedby.getText().toString());
            }
        });

    }
    private void getklins(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(AddPurchases.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);
                            mylist=new ArrayList<String>();
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String klinname = person.getString("klin_name");

                                mylist.add(klinname);

                            }
                            ArrayAdapter arrayAdapter=new ArrayAdapter(AddPurchases.this,R.layout.spinner_item,mylist);
                            spinner.setAdapter(arrayAdapter);
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
                params.put("user_mobile", mobile);

                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }
    private void getraw(){
        RequestQueue queue = Volley.newRequestQueue(AddPurchases.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);
                            mylist2=new ArrayList<String>();
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String raw_name = person.getString("mat_name");

                                mylist2.add(raw_name);

                            }
                            ArrayAdapter arrayAdapter=new ArrayAdapter(AddPurchases.this,R.layout.spinner_item,mylist2);
//                            spinner2.setAdapter(arrayAdapter);
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
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }
    private void addpurchase(final String matname,final String pdate, final String pprice,final String curentqty,final String klinname,final String pby){
        RequestQueue queue = Volley.newRequestQueue(AddPurchases.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstant.URL+url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        JSONObject person = null;
                        try {
                            person = new JSONObject(ServerResponse);
                            String success = person.getString("success");
                            String message = person.getString("message");
                            if (success.equals("1")){

                                Toast.makeText(AddPurchases.this, ""+message, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddPurchases.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(AddPurchases.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("raw_mat_name", matname);
                params.put("p_date", pdate);
                params.put("p_price", pprice);
                params.put("p_current_qty", curentqty);
                params.put("klin_name", klinname);
                params.put("p_by", pby);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
