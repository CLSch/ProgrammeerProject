package com.example.caitlin.feedbacksave;

import java.io.Serializable;

/**
 * Created by Caitlin on 16-06-16.
 */
public class Year implements Serializable {

    int id;
    String name;

    public Year(){

    }

    public String getNameYear() {
        return name;
    }

    public int getId() {
        return id;
    }
}
