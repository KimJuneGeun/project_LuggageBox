package com.example.luggagebox;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;

public class Apply extends AppCompatActivity {

    private ImageView btn_waiting;
    private ImageView btn_complete;
    private ImageView btn_instorage;
    private ImageView btn_deal_complete;
    private ImageView btnBack;
    private final static int CAMERA_PERMISSIONS_GRANTED = 100;

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

        getCameraPermission();


    }
    private boolean getCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            // 권한이 왜 필요한지 설명이 필요한가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {
                Toast.makeText(this, "카메라 사용을 위해 확인버튼을 눌러주세요!", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        CAMERA_PERMISSIONS_GRANTED);
                return true;
            }
        }
    }
}
