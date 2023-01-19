package com.example.restservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
@Document(collection ="mois")
public class Mois {

    @Id
    private String id;
    @Field("mois")
    private String mois;
    @Field("name")
    private String name;
    private ArrayList<Operation> listoperation;


    public Mois() {
    }

    public Mois(String mois,String name){
        this.mois=mois;
        this.name=name;
        listoperation=new ArrayList<Operation>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addoperation(Operation op){
        this.listoperation.add(op);
    }
    public String getMois() {
        return mois;
    }

    public ArrayList<Operation> getListoperation() {
        return listoperation;
    }
}
