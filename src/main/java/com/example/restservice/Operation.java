package com.example.restservice;

import java.util.ArrayList;

public class Operation {
    private  String date;
    private String operations;
    // ajout d'un file ,
    private String debit;
    private String credit;
    private String path;
    private String comment;
    public Operation(String date,String debit,String credit){
        this.date=date;
        this.credit=credit;
        this.debit=debit;
        this.operations="";
        this.path="";
        this.comment="";
    }

    public  String getDate() {
        return date;
    }
    public  void addoperation(String op){
        this.operations=this.operations+" "+op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCredit() {
        return credit;
    }

    public String getDebit() {
        return debit;
    }

    public String getOperations() {
        return operations;
    }
}
