package com.vaadin.trippy;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.router.NotFoundException;
import com.vaadin.router.Route;
import com.vaadin.router.Router;
import com.vaadin.trippy.data.Trip;
import com.vaadin.trippy.data.TripRepository;
import com.vaadin.trippy.impl.PageableDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.html.Div;

@Route("")
public class TripList extends Div {
    @Autowired
    private TripRepository tripRepository;

    @PostConstruct
    private void init() {
        Grid<Trip> grid = new Grid<>();
        grid.addColumn("Date", Trip::getFormattedDate);
        grid.addColumn("Length", Trip::getFormattedLength);
        grid.addColumn("Data", Trip::getData);

        add(createDiv("Trip count: " + tripRepository.count()));
        grid.setDataProvider(new PageableDataProvider<Trip, Long>() {
            @Override
            protected Page<Trip> fetchFromBackEnd(Query<Trip, Long> query,
                    Pageable pageable) {
                return tripRepository.findAll(pageable);
            }

            @Override
            protected List<QuerySortOrder> getDefaultSortOrders() {
                return Collections.emptyList();
            }

            @Override
            protected int sizeInBackEnd(Query<Trip, Long> query) {
                return (int) tripRepository.count();
            }
        });

        grid.asSingleSelect().addValueChangeListener(event -> {
            Trip trip = event.getValue();
            if (trip != null) {
                UI ui = getUI().get();
                Router router = (Router) ui.getRouter().get();
                try {
                    ui.navigateTo(router.getUrl(TripEditor.class,
                            String.valueOf(trip.getId())));
                } catch (NotFoundException e) {
                    // XXX Shouldn't really be this hard!
                    e.printStackTrace();
                }
            }
        });

        add(grid);
    }

    private static Div createDiv(String text) {
        Div div = new Div();
        div.setText(text);
        return div;
    }
}
