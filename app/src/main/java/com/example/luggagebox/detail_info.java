package com.example.luggagebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.map.MapFragment;

public class detail_info extends AppCompatActivity {

    private Button reButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_view, mapFragment).commit();
        }
        reButton = (Button) findViewById(R.id.btn_register);

        reButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goNextActivity = new Intent(getApplicationContext(), register.class);
                startActivity(goNextActivity);
            }
        });

    }
}
