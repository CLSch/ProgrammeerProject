package com.example.caitlin.feedbacksave;

import java.io.Serializable;

/**
 * Created by Caitlin on 18-06-16.
 */
public class Photo implements Serializable {
    int id; // waarom doet deze alsof hij al geinitialiseerd is???
    String name;
    int fbType = 1;

    Photo() {

    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
}
