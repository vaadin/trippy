package com.vaadin.trippy;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.flow.model.TemplateModel;
import com.vaadin.router.ParentLayout;
import com.vaadin.router.Route;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.ui.Tag;
import com.vaadin.ui.UI;
import com.vaadin.ui.button.Button;
import com.vaadin.ui.common.HasStyle;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.datepicker.DatePicker;
import com.vaadin.ui.polymertemplate.Id;
import com.vaadin.ui.polymertemplate.PolymerTemplate;
import com.vaadin.ui.textfield.TextField;

@Route("add")
@ParentLayout(MainLayout.class)
@Tag("trip-form")
@HtmlImport("TripForm.html")
public class AddTrip extends PolymerTemplate<TemplateModel>
        implements HasStyle {
    @Autowired
    private TripRepository tripRepository;

    @Id("date")
    private DatePicker datePicker;
    @Id("from")
    private TextField from;
    @Id("to")
    private TextField to;
    @Id("save")
    private Button saveButton;

    public AddTrip() {
        setClassName("add-trip");

        Binder<Trip> binder = new Binder<>();

        binder.forField(datePicker).bind(Trip::getDate, Trip::setDate);
        binder.forField(from).bind(Trip::getStart, Trip::setStart);
        binder.forField(to).bind(Trip::getEnd, Trip::setEnd);

        binder.addValueChangeListener(e -> DirectionSearch.getCurrent()
                .previewTrip(from.getValue(), to.getValue()));

        saveButton.addClickListener(e -> {
            Trip trip = new Trip();
            if (binder.writeBeanIfValid(trip)) {
                tripRepository.saveAndFlush(trip);

                UI ui = UI.getCurrent();
                String url = ui.getRouter().get().getUrl(TripList.class,
                        trip.getId());
                ui.navigateTo(url);
            }
        });

        DirectionSearch.getCurrent().showTrip(null);
    }
}
