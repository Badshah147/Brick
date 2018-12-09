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
import android.widget.CompoundButton;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AddBricks extends AppCompatActivity {

    TextView tvoldqty,tvoldqty_kachi;
    Button btnsave;
    EditText edtnewqty,edtdate;
    Spinner spinner;
    PrefManager prefManager;
    String url="getKlins.php";
    List<String> mylist;
    String klinname;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf;
    int count = 0;

    int pakiold_qty=0;
    int pakinewqty;
    int totalpaki=0;
    int totalkachiminus=0;
    int kachinewqty=0;
    int kachioldqty=0;

    String value="0";
    String url2="getkachi.php";
    String url3="getpaki.php";
    String url4="updatekachi.php";
    String url5="addpaki.php";

    int kachiminus=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bricks);
        spinner=(Spinner)findViewById(R.id.spinnerklin);
        tvoldqty=(TextView)findViewById(R.id.tvbricck_qty);
        tvoldqty_kachi=(TextView)findViewById(R.id.tvbricks_qty_kachi);
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
                getpaki(klinname);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setedtsdate();
        fdateclick();

      //  kachioldqty=Integer.valueOf(tvoldqty_kachi.getText().toString());

       btnsave.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String edtbrick_new_qty = edtnewqty.getText().toString();
                       if (mylist.isEmpty())
                       {
                           Toast.makeText(AddBricks.this,"بھٹا منتخب کریں",Toast.LENGTH_SHORT).show(); }
                       else if (edtbrick_new_qty.isEmpty())
                       {
                           Toast.makeText(AddBricks.this,"ایک یا زیادہ جگہ خالی ہے",Toast.LENGTH_SHORT).show();
                       }
                       else if (edtbrick_new_qty.length()>=1){
                           pakinewqty=Integer.parseInt(edtnewqty.getText().toString());
                           if(pakinewqty == 0)
                           { Toast.makeText(AddBricks.this,"صحیح مقدار درج کریں",Toast.LENGTH_SHORT).show();}
                           else
                               {

                               }
                       }

                   }
               });
    }

          private void getklins(final String mobile)
          {
              RequestQueue queue = Volley.newRequestQueue(AddBricks.this);
              StringRequest jsonObjRequest = new StringRequest(Request.Method.POST, AppConstant.URL+url,
                      new Response.Listener<String>() {
                          @Override
                          public void onResponse(String response) {

                              try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    mylist = new ArrayList<String>();
                                    for (int i = 0; i < jsonArray.length(); i++)
                                      {
                                          JSONObject person = (JSONObject) jsonArray.get(i);
                                          String klinname = person.getString("klin_name");
                                          mylist.add(klinname);
                                      }
                                      ArrayAdapter arrayAdapter = new ArrayAdapter(AddBricks.this,R.layout.spinner_item,mylist);
                                      spinner.setAdapter(arrayAdapter);
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              }

                          }
                      },
                      new Response.ErrorListener() {
                          @Override
                          public void onErrorResponse(VolleyError volleyError) {

                              Toast.makeText(AddBricks.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                          }
                      }) {
                  @Override
                  protected Map<String, String> getParams() {
                      Map<String, String> params = new HashMap<String, String>();
                      params.put("user_mobile", mobile);
                      return params;
                  }

              };
              queue.add(jsonObjRequest);


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
                new DatePickerDialog(AddBricks.this, date, myCalendar
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
    private void getpaki(final String klin){
        RequestQueue queue = Volley.newRequestQueue(AddBricks.this);
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
                                tvoldqty.setText(bricks_qty);

                            }
                            Toast.makeText(AddBricks.this, "gettingpakki", Toast.LENGTH_SHORT).show();
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
        RequestQueue queue = Volley.newRequestQueue(AddBricks.this);
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
                                tvoldqty_kachi.setText(bricks_qty);
                            }
                            Toast.makeText(AddBricks.this, "gettingkachi", Toast.LENGTH_SHORT).show();
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
