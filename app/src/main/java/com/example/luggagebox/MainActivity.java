package com.example.luggagebox;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        MapView mapView = new MapView(this);
//
//        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);
//
//        Intent intent = new Intent(this, Map.class);
//        startActivity(intent);

        Intent intent = new Intent(this, Map.class);
        startActivity(intent);


    }
}


