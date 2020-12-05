package com.example.luggagebox;

public class ApplyList {

    private String Name ;
    private String Kinds;
    private String Size;
    private String StartTime;
    private String EndTime;

    //주소 세팅
    public void setName(String name){
        Name = name;
    }
    //주소 얻어오기
    public String getName(){
        return this.Name;
    }

    //짐 종류 세팅
    public void setKinds(String kinds){
        Kinds = kinds;
    }
    //짐종류 얻어오기
    public String getKinds(){
        return this.Kinds;
    }

    // 사이즈 세팅
    public void setSize(String size){
        Size = size;
    }
    // 사이즈 얻어오기
    public String getSize(){
        return this.Size;
    }

    // 시작 시간 세팅
    public void setStartTime(String startTime){
        StartTime = startTime;
    }
    // 시작시간 얻어오기
    public String getStartTime(){
        return this.StartTime;
    }
    // 종료 시간 세팅
    public void setEndTime(String endTime){
        EndTime = endTime;
    }
    // 종료 시간 얻어오기
    public String getEndTime(){
        return this.EndTime;
    }
}