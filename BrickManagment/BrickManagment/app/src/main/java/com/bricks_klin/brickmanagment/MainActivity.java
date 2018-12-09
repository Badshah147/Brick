package com.bricks_klin.brickmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.bricks_klin.prefrences.PrefManager;

public class MainActivity extends AppCompatActivity {

    ImageButton imv_raw_mat,imv_emp,imv_Add_bricks,imv_Add_klin,imv_purchase,imv_sale,imv_records,imv_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imv_raw_mat=(ImageButton)findViewById(R.id.rawMaterialButton);
        imv_emp=(ImageButton)findViewById(R.id.workersButton);
        imv_Add_bricks=(ImageButton)findViewById(R.id.bricksButton);
        imv_Add_klin=(ImageButton)findViewById(R.id.imageButton4);
        imv_purchase=(ImageButton)findViewById(R.id.imageButton5);
        imv_sale=(ImageButton)findViewById(R.id.imageButton6);
        imv_records=(ImageButton)findViewById(R.id.imageButton7);
        imv_logout=(ImageButton)findViewById(R.id.imageButton8);
        imvclicks();
    }
    private void imvclicks(){
        imv_raw_mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddMaterial.class));
            }
        });

        imv_emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddEmployee.class));
            }
        });
        imv_Add_bricks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BrickMenu.class));
            }
        });
        imv_Add_klin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddKlin.class));
            }
        });
        imv_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddPurchases.class));
            }
        });imv_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddSales.class));
            }
        });
        imv_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BhattaList.class));
            }
        });
        imv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager=new PrefManager(MainActivity.this);
                prefManager.clearlogout(MainActivity.this);
                finish();
            }
        });


    }
}
