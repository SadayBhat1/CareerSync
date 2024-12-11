package com.finalproj.kleplacementapp;

public class Student {

    private String sname;
    private String spassword;
    private String sid;
    private String sphone;
    private String sperc;
    private String scompany;

    public Student(){

    }

    public Student (String sname,String spassword,String sid,String sphone,String sperc){
        this.sname=sname;
        this.spassword=spassword;
        this.sid=sid;
        this.sphone=sphone;
        this.sperc=sperc;
    }

    public String getScompany(){
        return scompany;
    }
    public void setCompany(String scompany){
        this.scompany=scompany;
    }

    public String getSname(){
        return sname;
    }
    public void setSname(String sname){
        this.sname=sname;
    }

    public String getSpassword(){
        return spassword;
    }
    public void setSpassword(String spassword){
        this.spassword=spassword;
    }

    public String getSid(){
        return sid;
    }
    public void setSid(String sid){
        this.sid=sid;
    }

    public String getSphone(){
        return sphone;
    }
    public void setSphone(String sphone){
        this.sphone=sphone;
    }

    public String getSperc(){
        return sperc;
    }
    public void setSperc(String sperc){
        this.sperc=sperc;
    }
}
