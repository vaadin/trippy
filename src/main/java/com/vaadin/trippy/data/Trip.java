package com.vaadin.trippy.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Trip {
    @Id
    @GeneratedValue
    private long id;

    private String data;

    public Trip() {
        // Hibernate constructor
    }

    public long getId() {
        return id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
