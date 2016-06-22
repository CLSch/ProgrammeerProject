/**
 * Photo.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import java.io.Serializable;

/**
 * Created by Caitlin on 18-06-16.
 */
public class Photo implements Serializable {
    int id; // waarom doet deze alsof hij al geinitialiseerd is???
    String name;
    int fbType = 1;
    String path;

    Photo() {

    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
