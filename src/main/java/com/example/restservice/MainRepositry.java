package com.example.restservice;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MainRepositry extends MongoRepository<Mois,String> {
    public ArrayList<Mois> findMoisByName(String name);
    public ArrayList<Mois> findMoisByMois(String mois);
}
