package com.example.luggagebox;
//리스트뷰에 사용될 아이템 선언
public class ListviewItem {
    private String Address ;
    //주소 세팅
    public void setAddress(String address){
        Address = address;
    }
    //주소 얻어오기
    public String getAddress(){
        return this.Address;
    }
}
