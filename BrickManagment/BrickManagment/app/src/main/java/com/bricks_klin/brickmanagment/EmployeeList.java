package com.bricks_klin.brickmanagment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.bricks_klin.Util.SetterGetterEmp;
import com.bricks_klin.adapter.EmpAdapter;
import com.bricks_klin.prefrences.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeList extends AppCompatActivity {

    ListView lv;
    String url="getemp.php";
    String url2="searchemp.php";
    PrefManager prefManager;
    String klinname;
    EmpAdapter empAdapter;
    EditText edtsearch;
    ArrayList<SetterGetterEmp> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        lv=(ListView)findViewById(R.id.lvemp);
        edtsearch=(EditText)findViewById(R.id.edtempsearch);

        prefManager=new PrefManager(this);
        klinname=prefManager.getBhatta();
        getemp(klinname);


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

                        searchemp(prefManager.getBhatta(),edtsearch.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });
    }
    private void getemp(final String klinname){
        RequestQueue queue = Volley.newRequestQueue(EmployeeList.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            arrayList=new ArrayList<SetterGetterEmp>();
                            JSONArray jArrayObj= new JSONArray (response);
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                int  Id = person.getInt("Id");
                                String emp_name = person.getString("emp_name");
                                String emp_age = person.getString("emp_age");
                                String emp_cell = person.getString("emp_cell");
                                String emp_addr = person.getString("emp_addr");
                                String klinid = person.getString("klinid");
                                String emp_Salary = person.getString("emp_Salary");
                                SetterGetterEmp setterGetterEmp=new SetterGetterEmp();
                                setterGetterEmp.setId(""+Id);
                                setterGetterEmp.setName(emp_name);
                                setterGetterEmp.setAge(emp_age);
                                setterGetterEmp.setCell(emp_cell);
                                setterGetterEmp.setAddr(emp_addr);
                                setterGetterEmp.setSalary(emp_Salary);
                                arrayList.add(setterGetterEmp);

                            }
                            empAdapter=new EmpAdapter(arrayList,EmployeeList.this);
                            lv.setAdapter(empAdapter);
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
    private void searchemp(final String klinname,final String empname){
        RequestQueue queue = Volley.newRequestQueue(EmployeeList.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            arrayList=new ArrayList<SetterGetterEmp>();
                            JSONArray jArrayObj= new JSONArray (response);
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                int  Id = person.getInt("Id");
                                String emp_name = person.getString("emp_name");
                                String emp_age = person.getString("emp_age");
                                String emp_cell = person.getString("emp_cell");
                                String emp_addr = person.getString("emp_addr");
                                String klinid = person.getString("klinid");
                                String emp_Salary = person.getString("emp_Salary");
                                SetterGetterEmp setterGetterEmp=new SetterGetterEmp();
                                setterGetterEmp.setId(""+Id);
                                setterGetterEmp.setName(emp_name);
                                setterGetterEmp.setAge(emp_age);
                                setterGetterEmp.setCell(emp_cell);
                                setterGetterEmp.setAddr(emp_addr);
                                setterGetterEmp.setSalary(emp_Salary);
                                arrayList.add(setterGetterEmp);

                            }
                            empAdapter=new EmpAdapter(arrayList,EmployeeList.this);
                            lv.setAdapter(empAdapter);
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
                params.put("emp_name", empname);
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }
}
