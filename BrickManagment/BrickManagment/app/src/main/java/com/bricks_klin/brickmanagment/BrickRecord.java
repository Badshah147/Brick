package com.bricks_klin.brickmanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class BrickRecord extends AppCompatActivity {
    String url2="getkachi.php";
    String url3="getpaki.php";
    PrefManager prefManager;
    TextView tvpaki,tvkachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brick_record);
        tvpaki=(TextView)findViewById(R.id.tvpakirem);
        tvkachi=(TextView)findViewById(R.id.tvkachirem);

        prefManager=new PrefManager(this);
        getkachi(prefManager.getBhatta());
        getpaki(prefManager.getBhatta());

    }
    private void getpaki(final String klin){
        RequestQueue queue = Volley.newRequestQueue(BrickRecord.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String bricks_qty = person.getString("bricks_qty");
                                tvpaki.setText(bricks_qty);

                            }
                            Toast.makeText(BrickRecord.this, "gettingpakki", Toast.LENGTH_SHORT).show();
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
    private void getkachi(final String klin){
        RequestQueue queue = Volley.newRequestQueue(BrickRecord.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);

                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String bricks_qty = person.getString("bricks_qty");
                                tvkachi.setText(bricks_qty);
                            }
                            Toast.makeText(BrickRecord.this, "gettingkachi", Toast.LENGTH_SHORT).show();
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

}
