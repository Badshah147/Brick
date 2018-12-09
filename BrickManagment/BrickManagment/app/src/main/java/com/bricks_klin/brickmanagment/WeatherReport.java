package com.bricks_klin.brickmanagment;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.bricks_klin.Util.SetterGetterP;
import com.bricks_klin.Util.SetterGetterW;
import com.bricks_klin.adapter.CustomGridPurchase;
import com.bricks_klin.adapter.CustomGridWeather;
import com.bricks_klin.prefrences.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WeatherReport extends AppCompatActivity {

    String url="getweather.php";
    PrefManager prefManager;
    ListView lv;
    ArrayList<SetterGetterW> arrayList;
    CustomGridWeather customGrid;
    TextView tvdegrees,tvdays,tvlocs;
    ImageView imvcircle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_report);
        lv=(ListView)findViewById(R.id.lvweather);
        tvdegrees=(TextView)findViewById(R.id.tvdegrees);
        tvdays=(TextView)findViewById(R.id.tvdayyyy);
        tvlocs=(TextView)findViewById(R.id.tvcityy);
        imvcircle=(ImageView)findViewById(R.id.imvabc);
        prefManager=new PrefManager(this);

            getweather(prefManager.getBhatta());
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView tv=(TextView)view.findViewById(R.id.tvdegree);
                    TextView tvvalue=(TextView)view.findViewById(R.id.tvvalue);
                    TextView tvday=(TextView)view.findViewById(R.id.tvday);
                    TextView tvloc=(TextView) view.findViewById(R.id.tvlocation);
                    ImageView imv=(ImageView)view.findViewById(R.id.imv);


                    tvdays.setText(tvday.getText().toString());
                    tvdegrees.setText(tv.getText().toString()+"");
                    tvlocs.setText(tvloc.getText().toString());
                    String val=tvvalue.getText().toString();
                    Drawable draw=null;
                    draw=imv.getDrawable();

                    imvcircle.setImageDrawable(draw);
//                    if (val.equals("1")){
//                        imvcircle.setImageResource(R.drawable.rain);
//                    }else if (val.equals("2")){
//                        imvcircle.setImageResource(R.drawable.sun);
//                    }else if (val.equals("3")){
//                        imvcircle.setImageResource(R.drawable.sunny);
//                    }
                    Toast.makeText(WeatherReport.this, ""+tv.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void getweather(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(WeatherReport.this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                AppConstant.URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            arrayList=new ArrayList<SetterGetterW>();
                            JSONArray jArrayObj= new JSONArray (response);
                            for (int i = 0; i < jArrayObj.length(); i++) {
                                JSONObject person = (JSONObject) jArrayObj.get(i);
                                String Id = person.getString("Id");
                                String w_value = person.getString("w_value");
                                String w_degree= person.getString("w_degree");
                                String loc_name = person.getString("loc_name");
                                String w_day = person.getString("w_day");
                                String klinid = person.getString("klinid");
                                SetterGetterW setterGetterW=new SetterGetterW();
                                setterGetterW.setId(Id);
                                setterGetterW.setValue(w_value);
                                setterGetterW.setDegree(w_degree);
                                setterGetterW.setLocname(loc_name);
                                setterGetterW.setWday(w_day);
                                setterGetterW.setKlinid(klinid);
                                arrayList.add(setterGetterW);
                            }
                            customGrid=new CustomGridWeather(arrayList,WeatherReport.this);
                            lv.setAdapter(customGrid);
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
                params.put("klin_name", mobile);

                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjRequest);

    }

}
