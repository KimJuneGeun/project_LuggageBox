package com.example.luggagebox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

     private ArrayList<ListviewItem> listViewItemList = new ArrayList<ListviewItem>() ;

    public ListViewAdapter(){

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
            convertView = inflater.inflate(R.layout.recently_address, parnet, false);
        }
        TextView AddressTextView = (TextView) convertView.findViewById(R.id.Address) ;

        ListviewItem listViewItem = listViewItemList.get(position);

        AddressTextView.setText(listViewItem.getAddress());
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
    public void addItem( String Address) {
        ListviewItem item = new ListviewItem();


        item.setAddress(Address);

        listViewItemList.add(item);
    }

    public void removeItem( int pos) {

        listViewItemList.remove(pos);
    }


}

