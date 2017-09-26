package com.vaadin.trippy;

import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToDoubleConverter;
import com.vaadin.flow.model.TemplateModel;
import com.vaadin.trippy.data.Trip;
import com.vaadin.ui.Tag;
import com.vaadin.ui.button.Button;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.datepicker.DatePicker;
import com.vaadin.ui.polymertemplate.Id;
import com.vaadin.ui.polymertemplate.PolymerTemplate;
import com.vaadin.ui.textfield.TextField;

@Tag("trip-form")
@HtmlImport("frontend://TripForm.html")
public class TripForm extends PolymerTemplate<TemplateModel> {
    private Binder<Trip> binder = new Binder<>();
    @Id("distance")
    private TextField distanceField;
    @Id("date")
    private DatePicker datePicker;
    @Id("save")
    private Button saveButton;
    private Trip trip;
    private Runnable saveHandler;

    public TripForm() {
        distanceField.setPreventInvalidInput(true);
        distanceField.setPattern("[.0-9]*");

        binder.forField(datePicker).asRequired("Please select a date")
                .bind(Trip::getDate, Trip::setDate);
        binder.forField(distanceField)
                .withConverter(
                        new StringToDoubleConverter(0d, "Must enter a number"))
                .asRequired("Must enter a number")
                .bind(Trip::getLength, Trip::setLength);

        saveButton.addClickListener(e -> {
            if (saveHandler != null && binder.writeBeanIfValid(trip)) {
                saveHandler.run();
            }
        });

        binder.addStatusChangeListener(
                event -> saveButton.setDisabled(event.hasValidationErrors()));
    }

    public void edit(Trip trip, Runnable saveHandler) {
        this.trip = trip;
        this.saveHandler = saveHandler;

        binder.readBean(trip);
    }

}
