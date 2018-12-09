package com.bricks_klin.brickmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BrickMenu extends AppCompatActivity {

    Button btngokachi,btngopaki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brick_menu);
        btngokachi=(Button)findViewById(R.id.btngokachi);
        btngopaki=(Button)findViewById(R.id.btngopaki);

        btngopaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrickMenu.this,AddBricks.class));
                finish();
            }
        });
        btngokachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrickMenu.this,KachiBricks.class));
                finish();
            }
        });
    }
}
