package com.example.luggagebox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.map.NaverMap;

public class activity_address_selection extends AppCompatActivity {

    private Button btnAdd;
    private EditText et;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adress_selection);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (ImageView) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        //키보드 숨기기 설정
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Focus가 맞처진 EditText //
        et = (EditText)  findViewById(R.id.edtItem) ;


        //리스트뷰 선언
        ListView list = (ListView) findViewById((R.id.customlistview_adress));
        //리스트 연결위한 어댑터 선언
        final ListViewAdapter adapter;

        adapter = new ListViewAdapter();
        list.setAdapter(adapter);

        final EditText edtItem = (EditText) findViewById(R.id.edtItem);
        // 확인 버튼 이벤트 처리
        btnAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //버튼 클릭 시 리스트뷰에 아이템 추가
                adapter.addItem(edtItem.getText().toString());
                //listview 갱신.
                adapter.notifyDataSetChanged();
                // 추가 메세지 출력
                Toast Add_toast = Toast.makeText(getApplicationContext(), R.string.Toast_add,Toast.LENGTH_SHORT);
                Add_toast.show();
                //버튼 클릭시 키보드 종료
                imm.hideSoftInputFromWindow(et.getWindowToken(), 0 );
            }

        });
        // 삭제 기능 이벤트 처리
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent , View view,
                                           int position, long id){
                //현재 선택된 아이템의 position 획득.
                adapter.removeItem(position);
                //listview 갱신.
                adapter.notifyDataSetChanged();
                //삭제 메세지 출력
                Toast Delete_toast = Toast.makeText(getApplicationContext(), R.string.Toast_delete,Toast.LENGTH_SHORT);
                Delete_toast.show();

                return false;
            }
        });
        btn_detail_info(btnAdd);
    }
    public void btn_detail_info(View v) {
        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent detail_intent=new Intent(activity_address_selection.this,detail_info.class);
                detail_intent.putExtra("location",et.getText().toString());
                startActivity(detail_intent);
            }
        });
    }
}