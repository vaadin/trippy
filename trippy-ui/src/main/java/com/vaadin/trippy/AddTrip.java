package com.vaadin.trippy;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.flow.model.TemplateModel;
import com.vaadin.router.ParentLayout;
import com.vaadin.router.Route;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.trippy.impl.TripMap;
import com.vaadin.ui.Tag;
import com.vaadin.ui.button.Button;
import com.vaadin.ui.common.HasStyle;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.datepicker.DatePicker;
import com.vaadin.ui.polymertemplate.Id;
import com.vaadin.ui.polymertemplate.PolymerTemplate;
import com.vaadin.ui.textfield.TextField;

@Tag("trip-form")
@HtmlImport("TripForm.html")
@Route("add")
@ParentLayout(MainLayout.class)
public class AddTrip extends PolymerTemplate<TemplateModel>
        implements HasStyle {
    @Autowired
    TripRepository repository;

    @Id("from")
    TextField from;
    @Id("to")
    TextField to;
    @Id("date")
    DatePicker date;
    @Id("save")
    Button saveButton;

    @PostConstruct
    private void init() {
        addClassName("add-trip");

        Binder<Trip> binder = new Binder<>();

        binder.bind(from, Trip::getStart, Trip::setStart);
        binder.bind(to, Trip::getEnd, Trip::setEnd);
        binder.bind(date, Trip::getDate, Trip::setDate);

        saveButton.addClickListener(e -> {
            Trip trip = new Trip();
            if (binder.writeBeanIfValid(trip)) {
                repository.save(trip);

                TripList.navigateTo(trip);
            }
        });

        TripMap.getCurrent().showTrip(null);

        binder.addValueChangeListener(e -> TripMap.getCurrent()
                .previewTrip(from.getValue(), to.getValue()));
    }
}
