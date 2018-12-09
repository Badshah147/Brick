package com.bricks_klin.brickmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Map;

public class AddKlin extends AppCompatActivity {
    String url="getusername.php";
    String url2="addklin.php";
    String url3="insertkachirow.php";
    String url4="insertpakirow.php";
    EditText edtname,edtadr;
    Button btnsave;
    TextView tvname;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_klin);
        edtname=(EditText)findViewById(R.id.edtklin_name);
        edtadr=(EditText)findViewById(R.id.edtklin_addr);
        btnsave=(Button)findViewById(R.id.btnadd_klin);
        tvname=(TextView)findViewById(R.id.tvname);
        prefManager=new PrefManager(this);
        getusername(prefManager.getcell());
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddKlin(edtname.getText().toString(),prefManager.getcell(),edtadr.getText().toString());

            }
        });

    }
    private void getusername(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(AddKlin.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);
//                            Toast.makeText(AllLeaves.this, ""+response, Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String username = person.getString("username");
                                String user_type = person.getString("user_type");
                                tvname.setText(username);
                                prefManager.setusername(username);
                            }
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
    private void AddKlin(final String klinname,final String mobile, final String klinaddr){
        RequestQueue queue = Volley.newRequestQueue(AddKlin.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstant.URL+url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        JSONObject person = null;
                        try {
                            person = new JSONObject(ServerResponse);
                            String success = person.getString("success");
                            String message = person.getString("message");
                            if (success.equals("1")){

                                Toast.makeText(AddKlin.this, ""+message, Toast.LENGTH_SHORT).show();
                                edtname.setText("");
                                edtadr.setText("");
                                addRowKachi(klinname);
                            }else {
                                Toast.makeText(AddKlin.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(AddKlin.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("klin_name", klinname);
                params.put("user_mobile", mobile);
                params.put("klin_addr", klinaddr);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    private void addRowPaki(final String klinname){
        RequestQueue queue = Volley.newRequestQueue(AddKlin.this);
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

                                Toast.makeText(AddKlin.this, ""+message, Toast.LENGTH_SHORT).show();
                                edtname.setText("");
                                edtadr.setText("");
                            }else {
                                Toast.makeText(AddKlin.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(AddKlin.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("klin_name", klinname);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    private void addRowKachi(final String klinname){
        RequestQueue queue = Volley.newRequestQueue(AddKlin.this);
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

                                Toast.makeText(AddKlin.this, ""+message, Toast.LENGTH_SHORT).show();
                                edtname.setText("");
                                edtadr.setText("");
                                addRowPaki(klinname);
                            }else {
                                Toast.makeText(AddKlin.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(AddKlin.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("klin_name", klinname);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }


}
