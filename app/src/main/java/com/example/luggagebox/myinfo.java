package com.example.luggagebox;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

public class myinfo extends AppCompatActivity {
    private ImageButton reButton;
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_update);

        // 프로필 정보 - 이름값 갱신
        TextView tv_username = (TextView) findViewById(R.id.TextView_myinfo_Username);
        tv_username.setText(SignIn.name);

        // 프로필 정보 - 이메일값 갱신
        TextView tv_email = (TextView) findViewById(R.id.TextView_myinfo_email);
        tv_email.setText(SignIn.Email);

        // 프로필 정보 - 전화번호값 갱신
        TextView tv_tel = (TextView) findViewById(R.id.TextView_myinfo_Tel);
        tv_tel.setText(SignIn.Tel);

        // 프로필 정보 - 사진 갱신
        ImageView iv_photo = (ImageView) findViewById(R.id.TextView_myinfo_photo);
        Glide.with(this).load(SignIn.MyImageLink).circleCrop().into(iv_photo);

        // 뒤로가기 버튼
        reButton = (ImageButton) findViewById(R.id.Button_back);
        reButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}