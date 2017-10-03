package com.vaadin.trippy;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.SortDirection;
import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.OptionalParameter;
import com.vaadin.router.ParentLayout;
import com.vaadin.router.Route;
import com.vaadin.router.Router;
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
public class TripList extends Div implements HasUrlParameter<String> {
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
            assert trip != null : "Shouldn't be possible to deselect with current Grid implementation";

            UI ui = UI.getCurrent();
            Router router = ui.getRouter().get();

            String url = router.getUrl(TripList.class,
                    String.valueOf(trip.getId()));

            // https://github.com/vaadin/flow/issues/2562
            if (url.startsWith("/")) {
                url = url.substring(1);
            }

            ui.navigateTo(url);
        });

        RouterLink addLink = new RouterLink("+", AddTrip.class);
        addLink.setClassName("add-link");

        add(grid, addLink);
    }

    @Override
    public void setParameter(BeforeNavigationEvent event,
            @OptionalParameter String tripId) {
        if (tripId != null) {
            Trip trip = tripRepository.findOne(new Long(tripId));

            // TODO Shouldn't be needed to do a dirty check here
            if (!trip.equals(grid.asSingleSelect().getValue())) {
                grid.asSingleSelect().setValue(trip);
            }

            DirectionSearch directionSearch = DirectionSearch.getCurrent();
            directionSearch.showRoute(trip.getStart(), trip.getEnd());
        } else {
            DirectionSearch.getCurrent().showRoute("", "");
        }
    }
}
