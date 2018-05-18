package com.vaadin.trippy;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.trippy.impl.SpringDataProviderBuilder;
import com.vaadin.trippy.impl.TripMap;

@Route(value = "", layout = MainLayout.class)
public class TripList extends Div implements HasStyle, HasUrlParameter<Long> {

    private final TripRepository repository;

    private Grid<Trip> grid = new Grid<>();

    public TripList(@Autowired TripRepository repository) {
        this.repository = repository;

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
        Long tripId = trip != null ? trip.getId() : null;

        UI.getCurrent().navigate(TripList.class, tripId);
    }

    @Override
    public void setParameter(BeforeEvent event,
            @OptionalParameter Long tripId) {
        Trip trip = tripId == null ? null
                : repository.findById(tripId).orElse(null);

        grid.asSingleSelect().setValue(trip);
        TripMap.getCurrent().showTrip(trip);
    }
}
