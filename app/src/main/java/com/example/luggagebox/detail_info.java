package com.example.luggagebox;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.FusedLocationSource;

import static com.example.luggagebox.Map.markers;

public class detail_info extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener{
    private String TAG = "출력";
    private Button reButton;
    private String location;
    private MapView mapView;
    private static NaverMap naverMap;
    CameraPosition cameraPosition;
//    CameraUpdate cameraUpdate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);


    //    cameraPosition = new CameraPosition(new LatLng(35.14566748378248, 129.03686644241608),15,45,45);
        Intent loc_info = getIntent();
        location = loc_info.getExtras().getString("location");

        Button btn = (Button) findViewById(R.id.btn_call);
        Button btn1 = (Button) findViewById(R.id.btn_chat);

        //네이버 지도
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map_view);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map_view, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);


        reButton = (Button) findViewById(R.id.btn_register);

        reButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goNextActivity = new Intent(getApplicationContext(), register.class);
                startActivity(goNextActivity);
            }
        });
        // 전화 아이콘 클릭시 다이얼 연결
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent call = new Intent();
                call.setAction(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:12345"));
                //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
                startActivity(call);
            }
        });
        // 메시지 아이콘 클릭시 메시지 연결
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:01046302904");
                Intent chat = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(chat);
            }
        });
    }

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {
            Toast.makeText(this.getApplicationContext(), "마커가 선택되었습니다", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
    //네이버 지도
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        if(location.contains("동의대학교")) {
            Log.d(TAG, "동의대학교 입력됨");
            cameraPosition = new CameraPosition(new LatLng(35.14555605664081, 129.03623986986486),15,45,45);
            naverMap.setCameraPosition(cameraPosition);
        }
        else if(location.contains("부산역")){
            Log.d(TAG, "부산역 입력됨");
            cameraPosition = new CameraPosition(new LatLng(35.1133916,129.0380028),15,45,45);
            naverMap.setCameraPosition(cameraPosition);
        }
        else {
            Log.d(TAG, "아무거나 입력됨");
            cameraPosition = new CameraPosition(new LatLng(35.1133916,129.0380028),15,45,45);
            naverMap.setCameraPosition(cameraPosition);
        }
        for(Marker m : markers) {
            m.setMap(naverMap);
        }
     //   CameraPosition cameraPosition = new CameraPosition(new LatLng(35.14566748378248, 129.03686644241608),15,45,45);
//        naverMap.setLayerGroupEnabled(naverMap.LAYER_GROUP_BUILDING, true);
//        this.naverMap = naverMap;
//        if(location.contains("동의대학교")) {
////            cameraPosition = new CameraPosition(new LatLng(35.113860862466865, 129.03784078384825),15,45,45);
//           // naverMap.setCameraPosition(cameraPosition);
//            cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.113860862466865, 129.03784078384825)).animate(CameraAnimation.Easing);
//
//        }
//        else if(location.contains("부산역")) {
////            cameraPosition = new CameraPosition(new LatLng(35.1133916,129.0380028),15,45,45);
//            cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.1133916,129.0380028)).animate(CameraAnimation.Easing);
//        }
//        naverMap.setMinZoom(17.0);
//        naverMap.setMaxZoom(18.0);
//        naverMap.moveCamera(cameraUpdate);
//        naverMap.setCameraPosition(cameraPosition);
    }
}
