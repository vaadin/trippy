package com.vaadin.trippy;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.Route;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;

@Route("trip")
public class TripEditor extends TripForm implements HasUrlParameter<String> {
    @Autowired
    private TripRepository tripRepository;

    @Override
    public void setParameter(BeforeNavigationEvent event, String parameter) {
        Trip trip = tripRepository.findOne(new Long(parameter));

        edit(trip, tripRepository::saveAndFlush);
    }

}
