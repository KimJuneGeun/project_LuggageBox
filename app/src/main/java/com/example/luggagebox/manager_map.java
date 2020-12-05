package com.example.luggagebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class manager_map extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView textView;
    private View ImageView;

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
    }
    public void btn_menu(View view) {
        if (view.getId() == R.id.btn_menu) {  // Buttoon의 ID를 찾아서 실행이 된다.
            drawerLayout.openDrawer(navigationView);
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
}