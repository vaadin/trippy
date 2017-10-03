package com.vaadin.trippy;

import java.util.function.Consumer;

import com.vaadin.data.Binder;
import com.vaadin.flow.model.TemplateModel;
import com.vaadin.trippy.data.Trip;
import com.vaadin.ui.Tag;
import com.vaadin.ui.UI;
import com.vaadin.ui.button.Button;
import com.vaadin.ui.common.HasStyle;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.datepicker.DatePicker;
import com.vaadin.ui.polymertemplate.Id;
import com.vaadin.ui.polymertemplate.PolymerTemplate;
import com.vaadin.ui.textfield.TextField;

@Tag("trip-form")
@HtmlImport("TripForm.html")
public class TripForm extends PolymerTemplate<TemplateModel>
        implements HasStyle {
    private Binder<Trip> binder = new Binder<>();
    @Id("date")
    private DatePicker datePicker;
    @Id("from")
    private TextField from;
    @Id("to")
    private TextField to;
    @Id("save")
    private Button saveButton;

    private Trip trip;
    private Consumer<Trip> saveHandler;

    public TripForm() {
        binder.forField(datePicker).asRequired("Please select a date")
                .bind(Trip::getDate, Trip::setDate);
        binder.forField(from).asRequired("Please enter a destination")
                .bind(Trip::getStart, Trip::setStart);
        binder.forField(to).asRequired("Please enter a starting point")
                .bind(Trip::getEnd, Trip::setEnd);

        binder.addValueChangeListener(e -> {
            DirectionSearch.getCurrent().previewRoute(from.getValue(),
                    to.getValue());
        });

        saveButton.addClickListener(e -> {
            if (saveHandler != null && binder.writeBeanIfValid(trip)) {
                saveHandler.accept(trip);

                UI ui = UI.getCurrent();

                String url = ui.getRouter().get().getUrl(TripList.class,
                        String.valueOf(trip.getId()));
                // https://github.com/vaadin/flow/issues/2562
                if (url.startsWith("/")) {
                    url = url.substring(1);
                }
                ui.navigateTo(url);
            }
        });
    }

    public void edit(Trip trip, Consumer<Trip> saveHandler) {
        this.trip = trip;
        this.saveHandler = saveHandler;

        binder.readBean(trip);
    }

}
