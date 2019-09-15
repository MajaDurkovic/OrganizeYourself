package com.mdurkovic.organizeyourself.Model;

import java.io.Serializable;

public class TaskModel implements Serializable {

    int id;
    String description, title;


    public TaskModel(){

    }
    public TaskModel(int id, String title, String description) {
        this.id = id;
        this.description = description;
        this.title = title;
    }

    public TaskModel(String title, String description) {
        this.description = description;
        this.title = title;
    }

    public  TaskModel(String description){
        this.description = description;
    }



    public int getTaskId() {
        return id;
    }

    public void setTaskId(int id) {
        this.id = id;
    }

    public String getTitle(){ return title; }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description= description;
    }


    @Override
    public String toString() {
        return this.title;
    }

}
