package com.vaadin.trippy;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.Route;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.ui.datepicker.DatePicker;
import com.vaadin.ui.html.Div;
import com.vaadin.ui.html.NativeButton;
import com.vaadin.ui.textfield.TextField;

@Route("trip")
public class TripEditor extends Div implements HasUrlParameter<String> {
    @Autowired
    private TripRepository tripRepository;

    private Binder<Trip> binder = new Binder<>();

    public TripEditor() {
        DatePicker datePicker = new DatePicker();
        TextField distanceField = new TextField();
        distanceField.setPreventInvalidInput(true);
        distanceField.setPattern("[.0-9]*");

        binder.forField(datePicker).asRequired("Please select a date")
                .bind(Trip::getDate, Trip::setDate);
        binder.forField(distanceField)
                .withConverter(
                        new StringToDoubleConverter(0d, "Must enter a number"))
                .asRequired("Must enter a number")
                .bind(Trip::getLength, Trip::setLength);

        NativeButton submitButton = new NativeButton("Save", e -> {
            if (binder.isValid()) {
                tripRepository.save(binder.getBean());
            }
        });

        binder.addStatusChangeListener(event -> {
            submitButton.getElement().setAttribute("disabled",
                    event.hasValidationErrors());
        });

        add(datePicker, distanceField, submitButton);
    }

    @Override
    public void setParameter(BeforeNavigationEvent event, String parameter) {
        Trip trip = tripRepository.findOne(new Long(parameter));

        binder.setBean(trip);
    }

}
