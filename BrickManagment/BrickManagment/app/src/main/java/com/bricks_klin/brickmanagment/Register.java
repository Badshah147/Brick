package com.bricks_klin.brickmanagment;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Register extends AppCompatActivity {

    EditText edtname,edtcell,edtccell,edtpass;
    Button btnreg;
    TextView tvgotoreg;
    String url="UserRegister.php";
    String randomcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtname=(EditText)findViewById(R.id.edtunameReg);
        edtcell=(EditText)findViewById(R.id.edtucellReg);
        edtccell=(EditText)findViewById(R.id.edtconcellReg);
        edtpass=(EditText)findViewById(R.id.edtpasReg);
        btnreg=(Button)findViewById(R.id.btnReg);
        tvgotoreg=(TextView)findViewById(R.id.tvgotoreg);

        tvgotoreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }
        }

        Random r = new Random();
       // randomcode = String.valueOf(r.nextInt(5000-1000)+1000);

        btnregclick();
    }



    private void btnregclick(){
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register(edtname.getText().toString(),edtcell.getText().toString(),edtpass.getText().toString());
            }
        });
    }

    private void Register(final String name,final String mobile, final String pass) {

        String confirm = edtccell.getText().toString();
        if (mobile.length() <= 10) {
            Toast.makeText(Register.this, "مکمل موبائل نمبر درج کریں", Toast.LENGTH_SHORT).show();
        } else if (!confirm.equals(mobile)) {
            Toast.makeText(Register.this, "موبائل نمبر مماثل نہیں ہے", Toast.LENGTH_SHORT).show();
        } else if (pass.length() < 5) {
            Toast.makeText(Register.this, "پاس ورڈ کے لئے کم سے کم پانچ الفاظ لکھیں", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty()) {
            Toast.makeText(Register.this, "نام لکھیں", Toast.LENGTH_SHORT).show();
        }
          else{
            RequestQueue queue = Volley.newRequestQueue(Register.this);
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
                                    Toast.makeText(Register.this, "برائے مہربانی انتظار کریں", Toast.LENGTH_SHORT).show();

                                    randomcode = "2233";
                                    sendSMS(edtcell.getText().toString(), "Your code is " + randomcode);
                                    PrefManager prefManager = new PrefManager(Register.this);
                                    prefManager.setrandcode(randomcode);
                                    prefManager.setcellreg(edtcell.getText().toString());

                                      new Timer().schedule(new TimerTask() {
                                          @Override
                                          public void run() {
                                              startActivity(new Intent(Register.this,UserVerification.class));
                                              finish();
                                          }
                                      },2000);


                                }else if (success.equals("0")){
                                          Toast.makeText(Register.this, "آپ کے پاس پہلے ہی اکاؤنٹ ہے", Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(Register.this, "غلطی! دوبارہ کوشش کریں", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            Toast.makeText(Register.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", name);
                    params.put("user_mobile", mobile);
                    params.put("user_pass", pass);
                    return params;
                }

            };
            queue.add(stringRequest);
        }


    }

    public void sendSMS(String phoneNumber,String message) {
        SmsManager smsManager = SmsManager.getDefault();


        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> parts = sms.divideMessage(message);
        int messageCount = parts.size();

        Log.i("Message Count", "Message Count: " + messageCount);

        ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        for (int j = 0; j < messageCount; j++) {
            sentIntents.add(sentPI);
            deliveryIntents.add(deliveredPI);
        }

        // ---when the SMS has been sent---
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS sent",
//                                Toast.LENGTH_SHORT).show();
//                        PrefManager prefManager=new PrefManager(Register.this);
//                        prefManager.setrandcode(randomcode);
//                        startActivity(new Intent(Register.this,UserVerification.class));
//                        finish();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(getBaseContext(), "Generic failure",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(getBaseContext(), "No service",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(getBaseContext(), "Null PDU",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(getBaseContext(), "Radio off",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
//        registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//
//                    case Activity.RESULT_OK:
//                        Toast.makeText(getBaseContext(), "SMS delivered",
//                                Toast.LENGTH_SHORT).show();
//
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(getBaseContext(), "SMS not delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(DELIVERED));
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
           /* sms.sendMultipartTextMessage(phoneNumber, null, parts, sentIntents, deliveryIntents); */
    }

  /**  public static int[] randomno(int start, int end, int count) {
        Random rng = new Random();

        int[] result = new int[count];
        int cur = 0;
        int remaining = end - start;
        for (int i = start; i < end && count > 0; i++) {
            double probability = rng.nextDouble();
            if (probability < ((double) count) / (double) remaining) {
                count--;
                result[cur++] = i;
            }
            remaining--;
        }
        return result;
    } **/

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,Manifest.permission.SEND_SMS}, 101);
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
