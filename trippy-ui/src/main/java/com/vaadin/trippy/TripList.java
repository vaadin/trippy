package com.vaadin.trippy;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.provider.SortDirection;
import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.OptionalParameter;
import com.vaadin.router.Route;
import com.vaadin.router.RouterLink;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.trippy.impl.SpringDataProviderBuilder;
import com.vaadin.trippy.impl.TripMap;
import com.vaadin.ui.UI;
import com.vaadin.ui.common.HasStyle;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.grid.Grid;
import com.vaadin.ui.html.Div;

@Route(value = "", layout = MainLayout.class)
// Workaround for incompatibility in 1.0.0.alpha3 of vaadin-grid-flow
@HtmlImport("bower_components/vaadin-grid/vaadin-grid.html")
public class TripList extends Div implements HasStyle, HasUrlParameter<Long> {

    @Autowired
    private TripRepository repository;

    private Grid<Trip> grid = new Grid<>();

    @PostConstruct
    public void init() {
        setClassName("trip-list");

        grid.addColumn(Trip::getFormattedDate, "Date");
        grid.addColumn(Trip::getStart, "From");
        grid.addColumn(Trip::getEnd, "To");

        grid.setDataProvider(SpringDataProviderBuilder.forRepository(repository)
                .withDefaultSort("date", SortDirection.DESCENDING).build());

        grid.asSingleSelect().addValueChangeListener(e -> {
            Trip trip = e.getValue();

            navigateTo(trip);
        });

        RouterLink addLink = new RouterLink("+", AddTrip.class);
        addLink.setClassName("add-link");

        add(grid, addLink);
    }

    public static void navigateTo(Trip trip) {
        UI ui = UI.getCurrent();
        String url = ui.getRouter().get().getUrl(TripList.class, trip.getId());

        ui.navigateTo(url);
    }

    @Override
    public void setParameter(BeforeNavigationEvent event,
            @OptionalParameter Long tripId) {
        Trip trip = tripId == null ? null
                : repository.findById(tripId).orElse(null);

        grid.asSingleSelect().setValue(trip);
        TripMap.getCurrent().showTrip(trip);
    }
}
