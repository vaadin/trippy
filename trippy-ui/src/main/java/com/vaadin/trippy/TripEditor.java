package com.vaadin.trippy;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.Route;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.ui.datepicker.DatePicker;
import com.vaadin.ui.html.Div;
import com.vaadin.ui.html.NativeButton;

@Route("trip")
public class TripEditor extends Div implements HasUrlParameter<String> {
    @Autowired
    private TripRepository tripRepository;

    private Binder<Trip> binder = new Binder<>();

    public TripEditor() {
        DatePicker datePicker = new DatePicker();

        binder.bind(datePicker, Trip::getDate, Trip::setDate);

        add(datePicker, new NativeButton("Save", e -> {
            tripRepository.save(binder.getBean());
        }));
    }

    @Override
    public void setParameter(BeforeNavigationEvent event, String parameter) {
        Trip trip = tripRepository.findOne(new Long(parameter));

        binder.setBean(trip);
    }

}
