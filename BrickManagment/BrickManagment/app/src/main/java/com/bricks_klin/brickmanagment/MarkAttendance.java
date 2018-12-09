package com.bricks_klin.brickmanagment;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import com.bricks_klin.Util.SetterGetterSales;
import com.bricks_klin.adapter.CustomGridSales;
import com.bricks_klin.prefrences.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MarkAttendance extends AppCompatActivity {

    String url="getempnames.php";
    String url2="markattendance.php";
    PrefManager prefManager;
    List<String> mylist;
    Spinner spinner;
    String empname;
    EditText edtdate;
    String attendance_value="Absent";
    CheckBox check;
    Button btnmark;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        spinner=(Spinner)findViewById(R.id.spinneremp);
        edtdate=(EditText)findViewById(R.id.edtselectdate);
        check=(CheckBox)findViewById(R.id.checkbox);
        btnmark=(Button)findViewById(R.id.btnattendance);


        prefManager=new PrefManager(this);

        getEmployees(prefManager.getBhatta());
        setedtsdate();
        fdateclick();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                empname=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.isChecked()){
                    attendance_value="Present";
                }else {
                    attendance_value="Absent";
                }
                addAttendance(empname,edtdate.getText().toString(),attendance_value,prefManager.getBhatta());
            }
        });
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
        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 1;
                new DatePickerDialog(MarkAttendance.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateDate(int c) {

            sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            edtdate.setText(sdf.format(myCalendar.getTime()));
            Log.e("DateSettingTwo", edtdate.getText().toString());

    }
    private void getEmployees(final String klinname){
        RequestQueue queue = Volley.newRequestQueue(MarkAttendance.this);
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
                                String emp_name = person.getString("emp_name");
                                mylist.add(emp_name);

                            }
                            ArrayAdapter arrayAdapter=new ArrayAdapter(MarkAttendance.this,R.layout.spinner_item,mylist);
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
                params.put("klin_name", klinname);

                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }
    private void addAttendance(final String empname,final String date, final String val,final String klinname){
        RequestQueue queue = Volley.newRequestQueue(MarkAttendance.this);
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

                                Toast.makeText(MarkAttendance.this, ""+message, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MarkAttendance.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(MarkAttendance.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("emp_name", empname);
                params.put("a_date", date);
                params.put("a_val", val);
                params.put("klin_name", klinname);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }


}
