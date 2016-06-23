/**
 * Year.java
 * Caitlin Schäffers
 * 10580441
 */

package com.example.caitlin.feedbacksave;

import java.io.Serializable;

/**
 * Class for a Year object. a Year contains a name and id.
 */
public class Year implements Serializable {
    int id;
    String name;

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
