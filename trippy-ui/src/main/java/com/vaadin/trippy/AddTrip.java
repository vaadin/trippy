package com.vaadin.trippy;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.router.ParentLayout;
import com.vaadin.router.Route;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;

@Route("add")
@ParentLayout(MainLayout.class)
public class AddTrip extends TripForm {
    @Autowired
    private TripRepository tripRepository;

    @PostConstruct
    private void init() {
        setClassName("add-trip");
        edit(new Trip(), tripRepository::saveAndFlush);
    }
}
