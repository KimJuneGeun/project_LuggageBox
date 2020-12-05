package com.example.luggagebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Apply extends AppCompatActivity {

    private ImageView btn_waiting;
    private ImageView btn_complete;
    private ImageView btn_instorage;
    private ImageView btn_deal_complete;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applylist);

        btn_complete = findViewById(R.id.btn_complete);
        btn_waiting = findViewById(R.id.btn_waiting);
        btn_instorage = findViewById(R.id.btn_instorage);
        btn_deal_complete = findViewById(R.id.btn_deal_complete);

        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent complete = new Intent(Apply.this, applycomplete.class);
                startActivity(complete);
            }
        });
        btn_waiting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent waiting = new Intent(Apply.this, wating.class);
                startActivity(waiting);
            }
        });
        btn_instorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent instorage = new Intent(Apply.this, com.example.luggagebox.instorage.class);
                startActivity(instorage);
            }
        });
        btn_deal_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deal_complete = new Intent(Apply.this, dealcomplete.class);
                startActivity(deal_complete);
            }
        });


    }
}
