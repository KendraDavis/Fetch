package com.example.fetch;

import android.util.Log;

public class DataModel {
    String name;
    int listId;
    int id;
    String color;


    public DataModel(String name, int listID, int id) {
        this.name = name;
        this.listId = listID;
        this.id = id;

    }


    public String getName() {
        return name;
    }

    public int getListId() {
        return listId;
    }

    public int getId() {
        return id;
    }


}
