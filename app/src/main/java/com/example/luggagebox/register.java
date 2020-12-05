package com.example.luggagebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity implements View.OnClickListener{
    private Button reButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reButton = (Button) findViewById(R.id.btn_register);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_submit) {
            Intent goNextActivity = new Intent(getApplicationContext(), register2.class);
            startActivity(goNextActivity);
            finish();
        }
    }
}
