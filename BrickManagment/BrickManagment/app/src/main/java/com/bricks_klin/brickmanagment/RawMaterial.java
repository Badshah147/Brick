package com.bricks_klin.brickmanagment;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.bricks_klin.Util.AppConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RawMaterial extends AppCompatActivity {
    PieChart pieChart ;
    ArrayList<Entry> entries ;
    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    String url="getrawpie.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_material);
        pieChart = (PieChart) findViewById(R.id.chart1);
        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();
        pieChart.setUsePercentValues(true);
        getraw();
//        AddValuesToPIEENTRY();
//
//        AddValuesToPieEntryLabels();

//        pieDataSet = new PieDataSet(entries, "");
//        pieDataSet.setValueTextColor(Color.WHITE);
//        pieDataSet.setValueTextSize(20);
//        pieData = new PieData(PieEntryLabels, pieDataSet);
//        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        pieChart.setData(pieData);
//
//        pieChart.animateY(3000);

    }

    public void AddValuesToPIEENTRY(){

        entries.add(new BarEntry(2f, 0));
        entries.add(new BarEntry(4f, 1));
        entries.add(new BarEntry(6f, 2));

    }

    public void AddValuesToPieEntryLabels(){
        PieEntryLabels.add("ریت");

        PieEntryLabels.add("کوئلہ");
        PieEntryLabels.add("کچی اینٹ");

    }
    private void getraw(){
        RequestQueue queue = Volley.newRequestQueue(RawMaterial.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONArray jArrayObj= new JSONArray (response);
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String raw_name = person.getString("mat_name");
                                int qty_perc=Integer.valueOf(person.getString("qty_perc"));

                                PieEntryLabels.add(raw_name);
                                if (qty_perc>1 && qty_perc <11){
                                    entries.add(new BarEntry(1f, i));
                                }else if (qty_perc>10 && qty_perc <21){
                                    entries.add(new BarEntry(2f, i));
                                }else if (qty_perc>20 && qty_perc <31){
                                    entries.add(new BarEntry(3f, i));
                                }else if (qty_perc>30 && qty_perc <41){
                                    entries.add(new BarEntry(4f, i));
                                }else if (qty_perc>40 && qty_perc <51){
                                    entries.add(new BarEntry(5f, i));
                                }else if (qty_perc>50 && qty_perc <61){
                                    entries.add(new BarEntry(6f, i));
                                }else if (qty_perc>60 && qty_perc <71){
                                    entries.add(new BarEntry(7f, i));
                                }else if (qty_perc>70 && qty_perc <91){
                                    entries.add(new BarEntry(8f, i));
                                }else if (qty_perc>80 && qty_perc <100){
                                    entries.add(new BarEntry(9f, i));
                                }



                            }
                            pieDataSet = new PieDataSet(entries, "");
                            pieDataSet.setValueTextColor(Color.WHITE);
                            pieDataSet.setValueTextSize(20);
                            pieData = new PieData(PieEntryLabels, pieDataSet);
                            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                            pieChart.setData(pieData);

                            pieChart.animateY(3000);
//                            spinner2.setAdapter(arrayAdapter);
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
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }
}
