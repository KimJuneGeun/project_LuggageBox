package com.example.luggagebox;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class register2 extends Activity {
    String TAG = "출력";
    Button qrButton;
    //DB
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private final static int CAMERA_PERMISSIONS_GRANTED = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        Intent intent = getIntent();
        String en_k = intent.getStringExtra("짐종류");
        String en_s = intent.getStringExtra("짐크기");
        String Date1 = intent.getStringExtra("시작날짜");
        String Date2 = intent.getStringExtra("시작시간");
        String Date3 = intent.getStringExtra("끝날짜");
        String Date4 = intent.getStringExtra("끝시간");
        String address_name = intent.getStringExtra("Address");
        String status = intent.getStringExtra("status");

        TextView en_ki=(TextView)findViewById(R.id.en_ki);
        TextView en_si=(TextView)findViewById(R.id.en_si);
        TextView Date11=(TextView)findViewById(R.id.Date1);
        TextView Date22=(TextView)findViewById(R.id.Date2);
        TextView Date33=(TextView)findViewById(R.id.Date3);
        TextView Date44=(TextView)findViewById(R.id.Date4);
        TextView address = (TextView)findViewById(R.id.address);
        TextView status_text = (TextView)findViewById(R.id.status);

        en_ki.setText(en_k);
        en_si.setText(en_s);
        Date11.setText(Date1);
        Date22.setText(Date2);
        Date33.setText(Date3);
        Date44.setText(Date4);
        address.setText(address_name);
       // CheckStatus();
        if(status.equals("0")) {
            status_text.setText("현재상태: 대기");
        }
        else if(status.equals("1")) {
            status_text.setText("현재상태: 예약완료");
        }
        else if(status.equals("2")) {
            status_text.setText("현재상태: 보관중");
        }
        else {
            status_text.setText("현재상태: 거래완료");
        }
        qrButton = (Button)findViewById(R.id.btn_QRcode);   // Button Boilerplate

        getCameraPermission();

        // 다음 Activity로 넘어가기 위한 onClickListener
        // 이렇게 한 이유는 Permission Check 가
        // 기본적으로 UI Thread가 아닌 다른 Thread 에서 동시에 실행되기 때문에
        // 첫 실행 때, 권한이 없어서 SurfaceView 에서 addCallback 처리를 제대로 못하는 상황이 생긴다.
        // 그래서 검은 화면이 나온다. 고로, 아예 Activity를 다르게 해줬다.
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goNextActivity = new Intent(getApplicationContext(), QRcodeCreate.class);
                startActivity(goNextActivity);
            }
        });
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

    private void CheckStatus() {


        mDatabase.collection("Luggagelist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getId().equals(SignIn.id)) {
                                    String n = document.getData().toString();
                                    int inx = n.indexOf("status=");
                                    String convertStatus = n.substring(inx + 7,n.length()-1);
                                    Log.d(TAG, n);
                                    Log.d(TAG, convertStatus);
                                  //  status = convertStatus;
                                    break;
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}