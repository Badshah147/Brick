package com.bricks_klin.brickmanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KachiBricks extends AppCompatActivity {

    Spinner spinner;
    PrefManager prefManager;
    String url="getKlins.php";
    List<String> mylist;
    String klinname;
    TextView tvoldqty;
    CheckBox checkBox;
    Button btnsave;
    EditText edtnewqty,edtdate;
    String url2="getkachi.php";
    int kachiold_qty=0;
    int kachinewqty=0;
    int totalkachi=0;
    String url3="updatekachi.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kachi_bricks);
        spinner=(Spinner)findViewById(R.id.spinnerklin);
        tvoldqty=(TextView)findViewById(R.id.tvbricck_qty);
        checkBox=(CheckBox)findViewById(R.id.checkkachi);
        btnsave=(Button)findViewById(R.id.btnaddbrick);
        edtdate=(EditText)findViewById(R.id.edtdate);
        edtnewqty=(EditText)findViewById(R.id.edtbrick_new_qty);


        prefManager=new PrefManager(this);

        getklins(prefManager.getcell());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                klinname=parent.getItemAtPosition(position).toString();
                getkachi(klinname);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kachiold_qty=Integer.valueOf(tvoldqty.getText().toString());
                kachinewqty=Integer.valueOf(edtnewqty.getText().toString());

                totalkachi=kachiold_qty+kachinewqty;
                addkachi(klinname,""+totalkachi,"01-08-2018");
            }
        });

    }
    private void getklins(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(KachiBricks.this);
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
                            ArrayAdapter arrayAdapter=new ArrayAdapter(KachiBricks.this,R.layout.spinner_item,mylist);
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
    private void getkachi(final String klin){
        RequestQueue queue = Volley.newRequestQueue(KachiBricks.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);
                            mylist=new ArrayList<String>();
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String bricks_qty = person.getString("bricks_qty");
                                tvoldqty.setText(bricks_qty);
                            }
                            Toast.makeText(KachiBricks.this, "gettingkachi", Toast.LENGTH_SHORT).show();
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
    private void addkachi(final String klinname,final String brickqty, final String datee){
        RequestQueue queue = Volley.newRequestQueue(KachiBricks.this);
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

                                Toast.makeText(KachiBricks.this, ""+message, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(KachiBricks.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(KachiBricks.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
}
