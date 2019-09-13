package com.mdurkovic.organizeyourself;

import java.io.Serializable;

public class TaskModel implements Serializable {

    private int id;
    private String description;


    public TaskModel(){

    }
    public TaskModel(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public  TaskModel(String description){
        this.description = description;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description= description;
    }

//    za datum
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String toString() {
        return this.description;
    }

}
