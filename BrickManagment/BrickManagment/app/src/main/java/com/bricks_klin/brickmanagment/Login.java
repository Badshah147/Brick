package com.bricks_klin.brickmanagment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {

    String url="login.php";
    EditText edtno,edtupass;
    Button btnlog;
    TextView gotoreg;
    String logval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtno=(EditText)findViewById(R.id.edtphno);
        edtupass=(EditText)findViewById(R.id.edtpas);
        btnlog=(Button)findViewById(R.id.loginButton);
        gotoreg=(TextView)findViewById(R.id.Account);

        PrefManager prefManager=new PrefManager(this);
        logval=prefManager.getLogval();
        if (logval.equals("1")){
            startActivity(new Intent(Login.this,MainActivity.class));
            finish();
        }else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (!checkIfAlreadyhavePermission()) {
                    requestForSpecificPermission();
                }
            }
            btnlog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Login(edtno.getText().toString(), edtupass.getText().toString());
                }
            });
            gotoreg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Login.this,Register.class));
                    finish();
                }
            });
        }

    }
    private void Login(final String cell, final String pass){

        if (pass.isEmpty() || cell.isEmpty()){
            Toast.makeText(Login.this, "ایک یا زیادہ جگہ خالی ہے", Toast.LENGTH_SHORT).show();
        }

     else{
        RequestQueue queue = Volley.newRequestQueue(Login.this);
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

                                Toast.makeText(Login.this, "خوش آمدید", Toast.LENGTH_SHORT).show();
                                PrefManager prefManager=new PrefManager(Login.this);
                                prefManager.setcell(edtno.getText().toString());
                                prefManager.setlogin("1");
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(Login.this,MainActivity.class));
                                        finish();
                                    }
                                },3000);

                            }else {
                                Toast.makeText(Login.this, "جعلی اسناد", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(Login.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_mobile", cell);
                params.put("user_pass", pass);
                return params;
            }

        };
        queue.add(stringRequest);
//        AppController.getInstance().addToRequestQueue(stringRequest);
    }}
    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,Manifest.permission.WAKE_LOCK,Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
