package com.example.luggagebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class past_list extends AppCompatActivity implements View.OnClickListener {

private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_list);
        //뒤로가기 기능
        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });


        //리스트뷰 선언
        ListView list = (ListView) findViewById((R.id.customlistview_past));
        //리스트 연결위한 어댑터 선언
        final pastAdapter adapter;

        adapter = new pastAdapter();
        list.setAdapter(adapter);
        // 확인 버튼 이벤트 처리
        adapter.addItem1("2020.10.27","동의대학교 지천관");
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

        /* 아이템 클릭시 작동 */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), past_info.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}