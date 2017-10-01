package com.vaadin.trippy.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Trip {
    @Id
    @GeneratedValue
    private long id;

    private LocalDate date;

    private String start;

    private String end;

    public Trip() {
        // Hibernate constructor
    }

    public long getId() {
        return id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getFormattedDate() {
        return date
                .format(DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US));
    }

    @Override
    public int hashCode() {
        return (int) id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (this.id == 0) {
            return false;
        }

        if (obj instanceof Trip) {
            Trip that = (Trip) obj;
            return this.id == that.id;
        } else {
            return false;
        }
    }
}
