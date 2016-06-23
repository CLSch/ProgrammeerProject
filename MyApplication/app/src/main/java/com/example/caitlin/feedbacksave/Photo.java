/**
 * Photo.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import java.io.Serializable;

/**
 * Class for Photo Object. Photo object contains an id, name and path where it's saved on dropbox.
 */
public class Photo implements Serializable {
    private int id;
    private String name;
    private String path;

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

    public void setPath(String path) {
        this.path = path;
    }
}
