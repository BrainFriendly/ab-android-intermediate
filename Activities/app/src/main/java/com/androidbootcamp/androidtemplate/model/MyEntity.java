package com.androidbootcamp.androidtemplate.model;

import java.io.Serializable;

/**
 * Created by emedinaa on 30/01/18.
 */

public class MyEntity implements Serializable {

    private int id;
    private String name;

    public MyEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
