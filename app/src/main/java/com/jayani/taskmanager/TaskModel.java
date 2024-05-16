package com.jayani.taskmanager;

public class TaskModel {
    Integer id;
    String title;
    String discription;
    String duedate;

    public TaskModel(String title, String discription, String duedate) {
        this.title = title;
        this.discription = discription;
        this.duedate = duedate;
    }

    public TaskModel(Integer id, String title, String discription, String duedate) {
        this.id = id;
        this.title = title;
        this.discription = discription;
        this.duedate = duedate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getDueDate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }
}
