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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMaterial extends AppCompatActivity {



    EditText edtname,edttype,edtqty;
    Button savemat;
    String url2="addmat.php";
    String url3="getrawbyId.php";
    String url4="updatemat.php";
    Spinner spinner;
    PrefManager prefManager;
    String url="getKlins.php";
    List<String> mylist;
    String klinname;
    Button btncheck;
    int valcheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);
        spinner=(Spinner)findViewById(R.id.spinnerklin);
        edtname=(EditText)findViewById(R.id.edtmat_name);
        edttype=(EditText)findViewById(R.id.edtmat_type);
        edtqty=(EditText)findViewById(R.id.edtprec);
        savemat=(Button)findViewById(R.id.btnadd_mat);
        btncheck=(Button)findViewById(R.id.btnchk);


        prefManager=new PrefManager(this);

        getklins(prefManager.getcell());


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                klinname=mylist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mylist.isEmpty()){
                    Toast.makeText(AddMaterial.this,"بھٹا منتخب کریں",Toast.LENGTH_SHORT).show();
                }else
                {getmatdetails(edtname.getText().toString());}
            }
        });
        savemat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (savemat.getText().toString().equals("خام مال اپ ڈیٹ کریں")){
                    if (mylist.isEmpty()){
                        Toast.makeText(AddMaterial.this,"بھٹا منتخب کریں",Toast.LENGTH_SHORT).show();
                    }else{
                    updatemat(edtname.getText().toString(),edttype.getText().toString(),klinname,edtqty.getText().toString());}
                }else {
                    if (mylist.isEmpty()){
                        Toast.makeText(AddMaterial.this,"بھٹا منتخب کریں",Toast.LENGTH_SHORT).show();
                    }else{
                    addmaterial(edtname.getText().toString(), edttype.getText().toString(), klinname,""+edtqty.getText().toString());}
                }
            }
        });
    }
    private void getklins(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(AddMaterial.this);
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
                            ArrayAdapter arrayAdapter=new ArrayAdapter(AddMaterial.this,R.layout.spinner_item,mylist);
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
    private void getmatdetails(final String matname){
        RequestQueue queue = Volley.newRequestQueue(AddMaterial.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);
                            if (jArrayObj.length()>0) {
                                for (int i = 0; i < jArrayObj.length(); i++) {
                                    JSONObject person = (JSONObject) jArrayObj.get(i);
                                    String matname = person.getString("mat_name");
                                    String mat_type = person.getString("mat_type");
                                    String klinid = person.getString("klinid");
                                    String qty_perc = person.getString("qty_perc");
                                    if (matname != null && qty_perc != null) {
                                        valcheck = 0;
                                        edttype.setText(mat_type);
                                        edtqty.setText(qty_perc);
                                        savemat.setText("خام مال اپ ڈیٹ کریں");
                                        edtname.setEnabled(false);
                                    }

                                }
                            }else {
                                valcheck = 1;
                                edtqty.setText("");
                                edttype.setText("");
                                edtname.setEnabled(true);
                                savemat.setText("نئی خام مال شامل کریں");

                                Toast.makeText(AddMaterial.this, "کوئی خام مال دستیاب نہیں ہے", Toast.LENGTH_SHORT).show();
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
                params.put("mat_name", matname);

                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }
    private void addmaterial(final String matname,final String mattype, final String klinname,final String qty){
        RequestQueue queue = Volley.newRequestQueue(AddMaterial.this);
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

                                Toast.makeText(AddMaterial.this, "خام مال شامل کر دیا گیا", Toast.LENGTH_SHORT).show();
                                edtname.setText("");
                                edttype.setText("");
                            }else {
                                Toast.makeText(AddMaterial.this, "دوبارہ کوشش کریں!!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(AddMaterial.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mat_name", matname);
                params.put("mat_type", mattype);
                params.put("klin_name", klinname);
                params.put("qty_perc", qty);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    private void updatemat(final String matname,final String mattype, final String klinname,final String qty){
        RequestQueue queue = Volley.newRequestQueue(AddMaterial.this);
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

                                Toast.makeText(AddMaterial.this, "خام مال کو اپ ڈیٹ کیا گیا ہے", Toast.LENGTH_SHORT).show();
                                edtname.setText("");
                                edttype.setText("");
                            }else {
                                Toast.makeText(AddMaterial.this, "دوبارہ کوشش کریں!!!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(AddMaterial.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mat_name", matname);
                params.put("mat_type", mattype);
                params.put("klin_name", klinname);
                params.put("qty_perc", qty);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
