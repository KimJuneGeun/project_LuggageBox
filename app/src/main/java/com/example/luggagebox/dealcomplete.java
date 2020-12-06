package com.example.luggagebox;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class dealcomplete extends AppCompatActivity {
    private ImageView btnBack;

    // AddJ - Firebase
    static String TestAddress = "";
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    FirebaseFirestore mDatabase1 = FirebaseFirestore.getInstance();
    private String TAG = "보관중 출력";

    FirebaseFirestore mDatabase2 = FirebaseFirestore.getInstance();

    static String Testname = "";
    static String ID1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_complete);
        //뒤로가기 기능
        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        //리스트뷰 선언
        ListView list = (ListView) findViewById((R.id.customlistview_deal_complete));
        //리스트 연결위한 어댑터 선언
        final dealcompleteAdapter adapter;

        adapter = new dealcompleteAdapter();
        list.setAdapter(adapter);

        // addJ - GetDB 호출
        getDB(adapter);

        // 확인 버튼 이벤트 처리
        //adapter.addItem("임도헌", "캐리어" , "middle" , "2020.10.27 15:00" , "2020.10.28 15:00");
        //listview 갱신.
        //adapter.notifyDataSetChanged();

        // 삭제 기능 이벤트 처리
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent , View view,
                                           int position, long id){
                //현재 선택된 아이템의 position 획득.
                adapter.removeItem(position);
                //listview 갱신
                adapter.notifyDataSetChanged();
                //삭제 메세지 출력
                Toast Delete_toast = Toast.makeText(getApplicationContext(), R.string.Toast_delete,Toast.LENGTH_SHORT);
                Delete_toast.show();

                return false;
            }
        });

    }

    // Add - DB 정보 가져옴
    private void getDB(final dealcompleteAdapter adapter) {
        getMGRAddress();
        //getStatus();

        mDatabase.collection("Luggagelist").whereEqualTo("status", 3).whereEqualTo("Address", TestAddress)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Test = document.getData().toString();

                                String[] Test2 = Test.split(",");

                                String Chkin = Test2[0];
                                String Chkin1 = Chkin.substring(11);

                                String Chkout = Test2[2];
                                String Chkout1 = Chkout.substring(12);

                                String ID = Test2[3];
                                ID1 = ID.substring(4);
                                Log.d(TAG, ID1);
                                getUsername(ID1);

                                String size = Test2[4];
                                String size1 = size.substring(9);

                                String title = Test2[5];
                                String title1 = title.substring(10);

                                adapter.addItem(Testname, title1 , size1 , Chkin1 , Chkout1);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
    private void getUsername(String ID){
        mDatabase2.collection("UserProfile").whereEqualTo("ID", Long.parseLong(ID))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Test1 = document.getData().toString();

                                String[] Test2 = Test1.split(",");
                                String Test3 = Test2[1];
                                Testname = Test3.substring(6);
                                //System.out.println("Hi-status-0-111111 : " + Test1);
                                Log.d(TAG, Testname);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    private void getMGRAddress(){
        mDatabase1.collection("UserProfile").whereEqualTo("ID", Long.parseLong(SignIn.id))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Test1 = document.getData().toString();

                                String[] Test2 = Test1.split(",");
                                String Test3 = Test2[5];
                                TestAddress = Test3.substring(12);
                                //System.out.println("Hi : " + TestAddress);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}