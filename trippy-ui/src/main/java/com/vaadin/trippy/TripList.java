package com.vaadin.trippy;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.SortDirection;
import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.OptionalParameter;
import com.vaadin.router.ParentLayout;
import com.vaadin.router.Route;
import com.vaadin.router.RouterLink;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.trippy.impl.SpringDataProviderBuilder;
import com.vaadin.ui.UI;
import com.vaadin.ui.grid.Grid;
import com.vaadin.ui.html.Div;

@Route("")
@ParentLayout(MainLayout.class)
public class TripList extends Div implements HasUrlParameter<Long> {
    @Autowired
    private TripRepository tripRepository;

    private final Grid<Trip> grid = new Grid<>();

    @PostConstruct
    private void init() {
        setClassName("trip-list");

        grid.addColumn("Date", Trip::getFormattedDate);
        grid.addColumn("From", Trip::getStart);
        grid.addColumn("To", Trip::getEnd);

        DataProvider<Trip, Void> dataProvider = SpringDataProviderBuilder
                .forRepository(tripRepository)
                .withDefaultSort("date", SortDirection.DESCENDING).build();

        grid.setDataProvider(dataProvider);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (!event.isFromClient()) {
                return;
            }
            Trip trip = event.getValue();

            UI ui = UI.getCurrent();
            String url = ui.getRouter().get().getUrl(TripList.class,
                    trip.getId());

            ui.navigateTo(url);
        });

        RouterLink addLink = new RouterLink("+", AddTrip.class);
        addLink.setClassName("add-link");

        add(grid, addLink);
    }

    @Override
    public void setParameter(BeforeNavigationEvent event,
            @OptionalParameter Long tripId) {
        if (tripId != null) {
            Trip trip = tripRepository.findOne(tripId);

            grid.asSingleSelect().setValue(trip);

            DirectionSearch directionSearch = DirectionSearch.getCurrent();
            directionSearch.showTrip(trip);
        } else {
            DirectionSearch.getCurrent().showTrip(null);
        }
    }
}
