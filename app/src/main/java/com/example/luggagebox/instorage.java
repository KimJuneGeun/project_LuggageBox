package com.example.luggagebox;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class instorage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instorage);


        //리스트뷰 선언
        ListView list = (ListView) findViewById((R.id.customlistview_instorage));
        //리스트 연결위한 어댑터 선언
        final instorageAdapter adapter;

        adapter = new instorageAdapter();
        list.setAdapter(adapter);
        // 확인 버튼 이벤트 처리
        adapter.addItem("임도헌", "캐리어" , "middle" , "2020.10.27 15:00" , "2020.10.28 15:00");
        //listview 갱신.
        adapter.notifyDataSetChanged();

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
}