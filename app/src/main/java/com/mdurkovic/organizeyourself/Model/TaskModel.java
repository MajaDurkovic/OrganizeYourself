package com.mdurkovic.organizeyourself.Model;

import java.io.Serializable;

public class TaskModel implements Serializable {

    int id;
    String title;
    String description;


    public TaskModel() {

    }

    public TaskModel(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public TaskModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.title;
    }
}