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

public class AddEmployee extends AppCompatActivity {
    String url2="addemp.php";
    Spinner spinner;
    String url="getKlins.php";
    List<String> mylist;
    String klinname;
    PrefManager prefManager;
    EditText edtname,edtage,edtcell,edtaddr,edtsalary;
    Button btnsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        spinner=(Spinner)findViewById(R.id.spinnerklin);
        edtname=(EditText)findViewById(R.id.edtemp_name);
        edtage=(EditText)findViewById(R.id.edtemp_age);
        edtcell=(EditText)findViewById(R.id.edtemp_cell);
        edtaddr=(EditText)findViewById(R.id.edtemp_addr);
        edtsalary=(EditText)findViewById(R.id.edtemp_salary);
        btnsave=(Button)findViewById(R.id.btnadd_emp);


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

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mylist.isEmpty()){
                    Toast.makeText(AddEmployee.this,"بھٹا منتخب کریں",Toast.LENGTH_SHORT).show();
                }else {
                    addemp(edtname.getText().toString(), edtage.getText().toString(), edtcell.getText().toString(), edtaddr.getText().toString(), klinname, edtsalary.getText().toString());
                }
            }
        });
    }
    private void getklins(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(AddEmployee.this);
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
                            ArrayAdapter arrayAdapter=new ArrayAdapter(AddEmployee.this,R.layout.spinner_item,mylist);
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
    private void addemp(final String name,final String age, final String cell,final String addr,final String klin_name,final String salary){

        if (name.isEmpty() || age.isEmpty() || cell.isEmpty() || addr.isEmpty() || salary.isEmpty())
        {
            Toast.makeText(AddEmployee.this,"ایک یا زیادہ جگہ خالی ہے",Toast.LENGTH_SHORT).show(); }
        else if(name.length()<3){
            Toast.makeText(AddEmployee.this,"صحیح نام لکھیں",Toast.LENGTH_SHORT).show(); }
        else if(cell.length()<=10){
            Toast.makeText(AddEmployee.this, "مکمل موبائل نمبر درج کریں", Toast.LENGTH_SHORT).show();
        }
        else {
        RequestQueue queue = Volley.newRequestQueue(AddEmployee.this);
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

                                Toast.makeText(AddEmployee.this, "ملازم کامیابی سے شامل ہوگیا", Toast.LENGTH_SHORT).show();
                                finish();
                            }else if (success.equals("0")) {
                                Toast.makeText(AddEmployee.this, "ملازم پہلے ہی موجود ہے", Toast.LENGTH_SHORT).show(); }
                             else{
                                Toast.makeText(AddEmployee.this, "دوبارہ کوشش کریں!!!", Toast.LENGTH_SHORT).show(); }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(AddEmployee.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("emp_name", name);
                params.put("emp_age", age);
                params.put("emp_cell", cell);
                params.put("emp_addr", addr);
                params.put("klin_name", klin_name);
                params.put("emp_salary", salary);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }}
}
