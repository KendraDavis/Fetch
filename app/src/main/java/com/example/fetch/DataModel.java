package com.example.fetch;

public class DataModel {
    String name;
    int listId;
    int id;

    /**
     * Constructor to setup the object
     * @param name name to be assigned to the object
     * @param listID listId to be assigned to the object
     * @param id id to be assigned to the object
     */
    public DataModel(String name, int listID, int id) {
        this.name = name;
        this.listId = listID;
        this.id = id;

    }

    /**
     *
     * @return the name of the object
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return the listId of the object
     */
    public int getListId() {
        return listId;
    }

    /**
     *
     * @return the id of the object
     */
    public int getId() {
        return id;
    }


}
