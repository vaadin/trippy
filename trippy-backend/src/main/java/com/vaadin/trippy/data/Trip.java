package com.vaadin.trippy.data;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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

    private double length;

    private String data = "";

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

    public void setLength(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public String getFormattedLength() {
        return createLengthFormat().format(length) + " mi";
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getFormattedDate() {
        return date
                .format(DateTimeFormatter.ofPattern("MM/dd/uuuu", Locale.US));
    }

    private static NumberFormat createLengthFormat() {
        NumberFormat numberFormat = DecimalFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(1);
        numberFormat.setMinimumFractionDigits(1);
        return numberFormat;
    }
}
