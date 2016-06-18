package com.example.caitlin.feedbacksave;

import java.io.Serializable;

/**
 * Created by Caitlin on 18-06-16.
 */
public class Subject implements Serializable {
    int id;
    String name;

    public Subject(){

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
