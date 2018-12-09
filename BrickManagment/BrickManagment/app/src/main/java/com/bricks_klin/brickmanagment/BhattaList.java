package com.bricks_klin.brickmanagment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

public class BhattaList extends AppCompatActivity {
    String url="getKlins.php";
    String url2="deletebhatta.php";
    LinearLayout li;
    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhatta_list);
        li=(LinearLayout)findViewById(R.id.liklins);
        prefManager=new PrefManager(this);
        getklins(prefManager.getcell());
    }
    private void createlayout(final String bhattaname){
        LinearLayout linearLayout=new LinearLayout(this);
        LinearLayout.LayoutParams parmas=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parmas.gravity=Gravity.CENTER;
        parmas.setMargins(0,20,0,0);
        linearLayout.setLayoutParams(parmas);

        final TextView textname=new TextView(this);
        LinearLayout.LayoutParams params2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 130);

        params2.setMargins(10,10,10,10);
        textname.setLayoutParams(params2);
        textname.setTextColor(Color.WHITE);
        textname.setPadding(16,16,16,16);
        textname.setBackgroundColor(ContextCompat.getColor(this,R.color.colorAccent));
        textname.setText(bhattaname);
        textname.setGravity(Gravity.CENTER);

        textname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BhattaList.this, ""+textname.getText().toString(), Toast.LENGTH_SHORT).show();
                prefManager.setbhatta(textname.getText().toString());
                startActivity(new Intent(BhattaList.this,MenuRecord.class));
                finish();
            }
        });
        textname.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DeleteBhatta(textname.getText().toString(),prefManager.getcell());
                finish();
                return false;
            }
        });
        linearLayout.addView(textname);
        li.addView(linearLayout);

    }
    private void getklins(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(BhattaList.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String klinname = person.getString("klin_name");
                                createlayout(klinname);
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

    private void DeleteBhatta(final String klinname,final String cell){
        RequestQueue queue = Volley.newRequestQueue(BhattaList.this);
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

                                Toast.makeText(BhattaList.this, ""+message, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(BhattaList.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(BhattaList.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("klin_name", klinname);
                params.put("user_mobile",cell);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
