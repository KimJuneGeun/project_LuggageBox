package com.example.luggagebox;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;

public class PhoneActivity extends AppCompatActivity {
    long id;
    String name;
    String TAG = "출력";
    //Firebase
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        Intent intent = getIntent();
        id = intent.getExtras().getLong("id");
        name = intent.getExtras().getString("name");

    }

    public void btn_Click(View view)
    {
        TextView textView = (TextView)findViewById(R.id.textView);
        EditText editText = (EditText)findViewById(R.id.editText);

        saveDB(editText.getText().toString(), name);

    }


    private void saveDB(String tel, String name){
        DocumentReference wash = mDatabase.collection("UserProfile").document(Long.toString(id));
        wash.update("isTel",true);
        wash.update("Tel",tel);
        redirectHomeActivity();

    }
    private void redirectHomeActivity() {
        startActivity(new Intent(PhoneActivity.this, Map.class));
        finish();
    }
}
