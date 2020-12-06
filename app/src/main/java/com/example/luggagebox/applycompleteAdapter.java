package com.example.luggagebox;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class applycompleteAdapter extends BaseAdapter {

    private ArrayList<ApplyList> listViewItemList = new ArrayList<ApplyList>() ;

    public applycompleteAdapter(){

    }
    @Override
    public int getCount(){
        return listViewItemList.size();
    }
    //각 데이터 항목에 대하여 리스트뷰에 표현하기위한 뷰를 생성하는 함수 inflater를 통해 커스텀 리스트뷰의 내용을 파싱하여
    //뷰들을 객체화 한다. 그리고 각 위젯의 참조를 얻어와 현재 표현하고자 하는 데이터 값들을 지정한다.
    @Override
    public View getView(int position, View convertView , ViewGroup parnet){
        final int pos = position;
        final Context context = parnet.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_apply_complete_item, parnet, false);
        }
        TextView NameTextView = (TextView) convertView.findViewById(R.id.Name) ;
        TextView KindsTextView = (TextView) convertView.findViewById(R.id.Kinds) ;
        TextView SizeTextView = (TextView) convertView.findViewById(R.id.Size) ;
        TextView StartTimeTextView = (TextView) convertView.findViewById(R.id.StartTime) ;
        TextView EndTimeTextView = (TextView) convertView.findViewById(R.id.EndTime) ;

        // 전화 버튼 클릭시 다이얼 이동
        ImageView call = (ImageView) convertView.findViewById(R.id.call);
        call.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent call = new Intent();
                call.setAction(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:01046302904"));
                //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
                context.startActivity(call);
            }
        });

        //메시지 이동
        ImageView chat = (ImageView) convertView.findViewById(R.id.message);
        chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("smsto:01046302904");
                Intent chat = new Intent(Intent.ACTION_SENDTO, uri);
                context.startActivity(chat);
            }
        });

        //qr 스캔 화면
        ImageView qr = (ImageView) convertView.findViewById(R.id.QRCode);

        qr.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), QRCodeScan.class);
                context.startActivity(intent);

            }

        });


        ApplyList listViewItem = listViewItemList.get(position);

        NameTextView.setText(listViewItem.getName());
        KindsTextView.setText(listViewItem.getKinds());
        SizeTextView.setText(listViewItem.getSize());
        StartTimeTextView.setText(listViewItem.getStartTime());
        EndTimeTextView.setText(listViewItem.getEndTime());

        return convertView;
    }
    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }
    public void addItem( String Name , String Kinds , String Size , String StartTime , String EndTime) {
        ApplyList item = new ApplyList();


        item.setName(Name);
        item.setKinds(Kinds);
        item.setSize(Size);
        item.setStartTime(StartTime);
        item.setEndTime(EndTime);
        listViewItemList.add(item);
    }

    public void removeItem( int pos) {

        listViewItemList.remove(pos);
    }


}

