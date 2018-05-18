package com.vaadin.trippy;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.trippy.impl.TripMap;

@Tag("trip-form")
@HtmlImport("TripForm.html")
@Route(value = "add", layout = MainLayout.class)
public class AddTrip extends PolymerTemplate<TemplateModel>
        implements HasStyle {

    @Id("from")
    TextField from;
    @Id("to")
    TextField to;
    @Id("date")
    DatePicker date;
    @Id("save")
    Button saveButton;

    public AddTrip(@Autowired TripRepository repository) {
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
