package com.bricks_klin.brickmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuRecord extends AppCompatActivity {

    TextView tvemp,tvbricks,tvkachibriks,tvsale,tvpurchase,tvrawmat,tvweather,tvaddattend,tvviewattend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_record);
        tvemp=(TextView)findViewById(R.id.tvmenuemp);
        tvbricks=(TextView)findViewById(R.id.tvmenubricks);
        tvsale=(TextView)findViewById(R.id.tvmenusales);
        tvpurchase=(TextView)findViewById(R.id.tvmenupurchases);
        tvrawmat=(TextView)findViewById(R.id.tvmenurawmat);
        tvkachibriks=(TextView)findViewById(R.id.tvmenukachibricks);
        tvweather=(TextView)findViewById(R.id.tvmenuweather);
        tvaddattend=(TextView)findViewById(R.id.tvaddattendance);
        tvviewattend=(TextView)findViewById(R.id.tvviewattendance);
        btnclisks();
    }
    private void btnclisks(){
        tvemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuRecord.this,EmployeeList.class));
            }
        });
        tvbricks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuRecord.this,BrickRecord.class));
            }
        });
        tvsale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuRecord.this,Sales.class));
            }
        });
        tvpurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuRecord.this,Purchases.class));
            }
        });
        tvrawmat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuRecord.this,RawMaterial.class));
            }
        });
        tvkachibriks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuRecord.this,WeatherReport.class));
            }
        });
        tvaddattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuRecord.this,MarkAttendance.class));
            }
        });
        tvviewattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuRecord.this,ViewAttendance.class));
            }
        });
    }
}
