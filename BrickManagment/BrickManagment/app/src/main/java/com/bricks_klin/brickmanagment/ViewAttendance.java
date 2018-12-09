package com.bricks_klin.brickmanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bricks_klin.Util.AppConstant;
import com.bricks_klin.Util.SetterGetterAttendance;
import com.bricks_klin.adapter.CustomGridAttendance;
import com.bricks_klin.prefrences.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewAttendance extends AppCompatActivity {

    ListView lv;
    PrefManager prefManager;
    ArrayList<SetterGetterAttendance> arrayList;
    String url="getattendance.php";
    CustomGridAttendance customGridAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        lv=(ListView)findViewById(R.id.lvatt);
        prefManager=new PrefManager(this);
        GetAttendance(prefManager.getBhatta());

    }
    private void GetAttendance(final String klinname){
        RequestQueue queue = Volley.newRequestQueue(ViewAttendance.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jArrayObj= new JSONArray (response);
                        arrayList=new ArrayList<SetterGetterAttendance>();

                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String emp_name = person.getString("emp_name");
                                String a_val=person.getString("a_val");
                                String a_date=person.getString("a_date");
                                SetterGetterAttendance setterGetterAttendance=new SetterGetterAttendance();
                                setterGetterAttendance.setEmpname(emp_name);
                                setterGetterAttendance.setValue(a_val);
                                setterGetterAttendance.setDate(a_date);
                                arrayList.add(setterGetterAttendance);

                            }
                           customGridAttendance=new CustomGridAttendance(arrayList,ViewAttendance.this);
                            lv.setAdapter(customGridAttendance);
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
}
