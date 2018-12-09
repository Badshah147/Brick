package com.bricks_klin.brickmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bricks_klin.Util.AppConstant;
import com.bricks_klin.prefrences.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class UserVerification extends AppCompatActivity {

    EditText edtcode;
    PrefManager prefManager;
    Button submitcode;
    String rancode;
    String url="update_user.php";
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);
        edtcode=(EditText)findViewById(R.id.edtcode);
        submitcode=(Button)findViewById(R.id.btnsubmitcode);
        prefManager=new PrefManager(this);
        rancode=prefManager.getRancode();
        mobile=prefManager.getcellreg();
    submitcode.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (rancode.equals(edtcode.getText().toString())){

                UpdateStatus(mobile);
            }
        }
    });

    }
    private void UpdateStatus(final String mobile){
        RequestQueue queue = Volley.newRequestQueue(UserVerification.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConstant.URL+url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        JSONObject person = null;
                        try {
                            person = new JSONObject(ServerResponse);
                            String success = person.getString("success");
                            String message = person.getString("message");
                            if (success.equals("1")){
                                Toast.makeText(UserVerification.this, "آپ رجسٹرڈ ہوں گے", Toast.LENGTH_SHORT).show();

                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(UserVerification.this,Login.class));
                                        finish();
                                    }
                                },3000);

                            }else {
                                Toast.makeText(UserVerification.this, ""+message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(UserVerification.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_mobile", mobile);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
