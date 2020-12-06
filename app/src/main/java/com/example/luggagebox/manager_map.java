package com.example.luggagebox;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class manager_map extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView textView;
    private View ImageView;

    // Add - Firebase
    static String TestMGRAddress = "";
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private String TAG = "MGRMap 출력";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_map);

        textView = findViewById(R.id.textView);
        drawerLayout = findViewById(R.id.drawer_layout_manager);
        ImageView = (View) findViewById(R.id.btn_address);
        navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);
        //헤더메인 들고오기
        View manager = navigationView.getHeaderView(0);
        ImageView Btn_User = manager.findViewById(R.id.btn_User);

        Btn_User.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent manager = new Intent(manager_map.this, Map.class);
                startActivity(manager);
                finish();
            }
        });
        getMGRAddress();
    }
    public void btn_menu(View view) {
        if (view.getId() == R.id.btn_menu) {  // Buttoon의 ID를 찾아서 실행이 된다.
            drawerLayout.openDrawer(navigationView);

            // Add - 프로필 추가
            // 개인 프로필 정보 넣을 수 있는 위치 지정
            navigationView.setNavigationItemSelectedListener(this);
            View header = navigationView.getHeaderView(0);

            // 프로필 정보 - 이름값 갱신
            TextView tv_username = (TextView) header.findViewById(R.id.textview_username);
            tv_username.setText(TestMGRAddress);

            // 프로필 정보 - 이메일값 갱신
            TextView tv_email = (TextView) header.findViewById(R.id.textview_email);
            tv_email.setText(SignIn.Email);

            // 프로필 정보 - 사진 갱신
            ImageView iv_photo = (ImageView) header.findViewById(R.id.ImageView_Photo);

            // Add - 사진이 없을 경우 기본 이미지 출력
            if(SignIn.MyImageLink == null) {
                Drawable drawable = getResources().getDrawable(R.drawable.user);
                iv_photo.setImageDrawable(drawable);
            }

            else {
                Glide.with(this).load(SignIn.MyImageLink).circleCrop().into(iv_photo);
            }
        }
    }
    public void btn_address (View view){
        ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Adress_intent = new Intent(manager_map.this, activity_address_selection.class);
                startActivity(Adress_intent);
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.manager_nav1://내정보
                Intent info = new Intent(manager_map.this, myinfo.class);
                startActivity(info);
                break;
            case R.id.manager_nav2://신청내역
                Intent now = new Intent(manager_map.this, Apply.class);
                startActivity(now);
                break;
            case R.id.manager_nav3:// 로그아웃
                UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Toast.makeText(manager_map.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                finish();
                Intent back = new Intent(manager_map.this, SignIn.class);
                startActivity(back);
                break;
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // AddJ - 주소 가져오기
    private void getMGRAddress(){
        mDatabase.collection("UserProfile").whereEqualTo("ID", Long.parseLong(SignIn.id))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Test1 = document.getData().toString();

                                String[] Test2 = Test1.split(",");
                                String Test3 = Test2[5];
                                TestMGRAddress = Test3.substring(12);
                                Log.d(TAG, TestMGRAddress);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}