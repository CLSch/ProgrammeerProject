/**
 * Year.java
 * Caitlin Schäffers
 * 10580441
 */

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
