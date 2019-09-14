package com.mdurkovic.organizeyourself.Model;


public class TaskModel {

    int id;
    String description, date;


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

 //   za datum
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return this.description;
    }

}
