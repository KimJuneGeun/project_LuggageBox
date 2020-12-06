package com.example.luggagebox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

public class Map extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, Overlay.OnClickListener {
    private String TAG = "출력";
    //네이버지도 변수
    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap, cpNaverMap;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    
    // AddJ - Firebase
    static String TestPerm = "";
    FirebaseFirestore mDatabase1 = FirebaseFirestore.getInstance();

    //마커
    static List<Marker> markers = new ArrayList<>();

    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.1133916, 129.0380028)).animate(CameraAnimation.Easing);
    //DB
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();


    //네비게이션 변수
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView textView;
    private View ImageView;
    private InfoWindow mInfoWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // 현재위치 네이버 지도 객체 생성
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_view, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        // 위치를 반환하는 구현체인 FusedLocationSource 생성
        mLocationSource =
                new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        // 네비게이션 변수 연결 및 초기화
        textView = findViewById(R.id.textView);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ImageView = (View) findViewById(R.id.btn_address);
        navigationView.setNavigationItemSelectedListener(this);
        //헤더메인 들고오기
        View manager = navigationView.getHeaderView(0);
        ImageView Btn_manager = manager.findViewById(R.id.btn_manager);
        Btn_manager.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Add - 권한 없으면 오류 메시지 출력
                if(TestPerm.equals("false")){
                    Toast.makeText(getApplicationContext(), "관리자가 아닙니다.", Toast.LENGTH_SHORT).show();
                }

                else{
                    Intent manager = new Intent(Map.this, manager_map.class);
                    startActivity(manager);
                    finish();
                }
            }
        });
    }

    //마커 생성
    private void marker(final NaverMap naverMap) {
        mDatabase.collection("locations")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Marker marker = new Marker();
                                String n = document.getId().toString();
                                Log.d(TAG,n);
                                String s = document.getData().toString();
                                int idx = s.indexOf(",");
                                String lng = s.substring(5,idx);
                                String lat = s.substring(idx+6, s.length()-1);
                                Log.d("출력",lat);
                                Log.d("출력",lng);
                                double lati = Double.parseDouble(lat);
                                double lngi = Double.parseDouble(lng);
                                marker.setPosition(new LatLng(lati,lngi));
                                marker.setTag(n);
                                markers.add(marker);

                                Log.d(TAG,"마커 저장");
//                                Log.d(TAG,lati);
//                                Log.d(TAG,lng);
                                }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        for(Marker m : markers) {
                            m.setMap(naverMap);

                        }
                    }
                });

    }

    //네이버 지도
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
//        mInfoWindow = new InfoWindow();
        cpNaverMap = naverMap;
        Log.d( TAG, "onMapReady");
        marker(naverMap);
        for(Marker m : markers) {
            m.setOnClickListener(this);
        }
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);
        // 권한확인. 결과는 onRequestPermissionsResult 콜백 매서드 호출
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);


    }
    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {
            Marker marker = (Marker) overlay;
            Log.d(TAG,marker.getTag().toString());
//            if (marker.getInfoWindow() != null) {
//                mInfoWindow.close();
//                Toast.makeText(this.getApplicationContext(), "InfoWindow Close.", Toast.LENGTH_LONG).show();
//            }
//            else {
//                mInfoWindow.open(marker);
//                Toast.makeText(this.getApplicationContext(), "InfoWindow Open.", Toast.LENGTH_LONG).show();
//            }
            Toast.makeText(this.getApplicationContext(), "마커가 선택되었습니다", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }

    // 네비게이션 메뉴 버튼
    public void btn_menu(View view) {
        if (view.getId() == R.id.btn_menu) {  // Buttoon의 ID를 찾아서 실행이 된다.
            // AddJ - 권한 불러오기
            getPerm();
            drawerLayout.openDrawer(navigationView);
//            cpNaverMap.setMinZoom(17.0);
//            cpNaverMap.setMaxZoom(18.0);
//            cpNaverMap.moveCamera(cameraUpdate);

            // 개인 프로필 정보 넣을 수 있는 위치 지정
            navigationView.setNavigationItemSelectedListener(this);
            View header = navigationView.getHeaderView(0);

            // 프로필 정보 - 이름값 갱신
            TextView tv_username = (TextView) header.findViewById(R.id.textview_username);
            tv_username.setText(SignIn.name);

            // 프로필 정보 - 이름값 갱신
            TextView tv_email = (TextView) header.findViewById(R.id.textview_email);
            tv_email.setText(SignIn.Email);

            // 프로필 정보 - 사진 갱신
            ImageView iv_photo = (ImageView) header.findViewById(R.id.ImageView_Photo);

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
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.1133916, 129.0380028)).animate(CameraAnimation.Easing);
        ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Adress_intent = new Intent(Map.this, activity_address_selection.class);
                startActivity(Adress_intent);

            }
        });

//        Intent location_info = getIntent();
//        loc_info = location_info.getExtras().getString("location");
//        if(loc_info != null) {
//            cpNaverMap.setMinZoom(17.0);
//            cpNaverMap.setMaxZoom(18.0);
//            cpNaverMap.moveCamera(cameraUpdate);
//        }
    }
    // 네비게이션 아이템 스위쳐
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav1://내정보
                Intent info = new Intent(Map.this, myinfo.class);
                startActivity(info);
                break;
            case R.id.nav2://현재 맡긴 짐
                Intent now = new Intent(Map.this, register2.class);
                startActivity(now);
                break;
            case R.id.nav3:// 과거 맡긴 짐
                Intent past = new Intent(Map.this, past_list.class);
                startActivity(past);
                break;
            case R.id.nav4:// 관리자 신청
                Intent web = new Intent(Map.this, manager_webview.class);
                startActivity(web);
                break;
            case R.id.nav5:// 로그아웃
                UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Toast.makeText(Map.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                finish();
                Intent back = new Intent(Map.this, SignIn.class);
                startActivity(back);
                break;
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    
    // AddJ - 권한 받아오기
    private void getPerm(){
        mDatabase1.collection("UserProfile").whereEqualTo("ID", Long.parseLong(SignIn.id))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Test1 = document.getData().toString();

                                String[] Test2 = Test1.split(",");
                                String Test3 = Test2[0];
                                TestPerm = Test3.substring(7);
                                System.out.println(TestPerm);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }

}