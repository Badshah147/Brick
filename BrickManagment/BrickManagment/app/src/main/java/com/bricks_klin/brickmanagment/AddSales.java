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

public class AddSales extends AppCompatActivity {

    Spinner spinner,spinner2;
    PrefManager prefManager;
    String url="getKlins.php";
    List<String> mylist;
    String klinname;
    String url3="getpaki.php";
    String url4="addsale.php";
    String url5="addpaki.php";
    int pakiqty;
    int pakitotl;
    int pakisale;
    int pakirem;
    EditText edtqty,edtprice,edtdate,edtsaleby,edtsaleto;
    Button btnadd;
    String todate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);
        spinner=(Spinner)findViewById(R.id.spinnerklin);
        edtqty=(EditText)findViewById(R.id.edtbrickqtysale);
        edtprice=(EditText)findViewById(R.id.edtbrickpricesale);
        edtdate=(EditText)findViewById(R.id.edtdatesale);
        edtsaleby=(EditText)findViewById(R.id.edtsaleby);
        edtsaleto=(EditText)findViewById(R.id.edtsaleto);
        btnadd=(Button)findViewById(R.id.btnsale);
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
                getpaki(klinname);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pakiqty=Integer.parseInt(edtqty.getText().toString());
                pakitotl=pakirem-pakiqty;
                if (pakitotl<1){
                    Toast.makeText(AddSales.this, "No Paki EEnt Remaining", Toast.LENGTH_SHORT).show();
                }else {
                    Addsale(edtqty.getText().toString(),edtprice.getText().toString(),edtdate.getText().toString(),edtsaleby.getText().toString(),edtsaleto.getText().toString(),klinname);
                    Log.e("PakiQuantitysale",""+pakiqty);
                }
            }
        });
    }
    private void getklins(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(AddSales.this);
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
                            ArrayAdapter arrayAdapter=new ArrayAdapter(AddSales.this,R.layout.spinner_item,mylist);
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
    private void getpaki(final String klin){
        RequestQueue queue = Volley.newRequestQueue(AddSales.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);
                            mylist=new ArrayList<String>();
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String bricks_qty = person.getString("bricks_qty");
                                pakirem+=Integer.valueOf(bricks_qty);
                                Log.e("PakiEentQty",""+pakirem);

                            }
                            Toast.makeText(AddSales.this, "gettingpakki", Toast.LENGTH_SHORT).show();
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
                params.put("klin_name", klin);

                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }
    private void addpaki(final String klinname,final String brickqty, final String datee){
        RequestQueue queue = Volley.newRequestQueue(AddSales.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstant.URL+url5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        JSONObject person = null;
                        try {
                            person = new JSONObject(ServerResponse);
                            String success = person.getString("success");
                            String message = person.getString("message");
                            if (success.equals("1")){

                                Toast.makeText(AddSales.this, ""+message, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddSales.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(AddSales.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("klin_name", klinname);
                params.put("bricks_qty", brickqty);
                params.put("datee", datee);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    private void Addsale(final String brickqty,final String brickprice, final String datee,final String saleby,final String saleto,final String klinname){
        RequestQueue queue = Volley.newRequestQueue(AddSales.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstant.URL+url4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        JSONObject person = null;
                        try {
                            person = new JSONObject(ServerResponse);
                            String success = person.getString("success");
                            String message = person.getString("message");
                            if (success.equals("1")){
                                addpaki(klinname,""+pakitotl,todate);
                                Toast.makeText(AddSales.this, ""+message, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(AddSales.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(AddSales.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("s_brick_qty", brickqty);
                params.put("s_price", brickprice);
                params.put("s_date", datee);
                params.put("s_by", saleby);
                params.put("s_to", saleto);
                params.put("klin_name", klinname);

                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }
}
