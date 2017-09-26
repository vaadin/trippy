package com.vaadin.trippy;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.Route;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.ui.html.Div;

@Route("trip")
public class TripEditor extends Div implements HasUrlParameter<String> {
    @Autowired
    private TripRepository tripRepository;

    @Override
    public void setParameter(BeforeNavigationEvent event, String parameter) {
        Trip trip = tripRepository.findOne(new Long(parameter));

        setText(trip.getFormattedDate());
    }

}
