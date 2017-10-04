package com.vaadin.trippy.impl;

import com.vaadin.server.VaadinSession;
import com.vaadin.trippy.data.Trip;
import com.vaadin.ui.Component;
import com.vaadin.ui.Tag;
import com.vaadin.ui.UI;
import com.vaadin.ui.common.HtmlImport;

import elemental.json.Json;
import elemental.json.JsonObject;

@HtmlImport("DirectionSearch.html")
@Tag("direction-search")
public class TripMap extends Component {
    public TripMap() {
        // Prevent accidentally constructing without associating with a UI
    }

    public static TripMap getCurrent() {
        // Fetch from a session attribute as a workaround for not missing UI
        // scope support
        UI ui = UI.getCurrent();
        VaadinSession session = ui.getSession();

        String attributeName = TripMap.class.getName() + ui.getUIId();
        TripMap directionSearch = (TripMap) session
                .getAttribute(attributeName);
        if (directionSearch == null) {
            directionSearch = new TripMap();
            ui.getSession().setAttribute(attributeName, directionSearch);
        }
        return directionSearch;
    }

    public void showTrip(Trip trip) {
        if (trip == null) {
            showTrip("", "");
        } else {
            showTrip(trip.getStart(), trip.getEnd());
        }
    }

    private void showTrip(String start, String end) {
        showRoute(start, end, false);
    }

    public void previewTrip(String start, String end) {
        showRoute(start, end, true);
    }

    private void showRoute(String start, String end, boolean debounce) {
        JsonObject properties = Json.createObject();
        properties.put("start", start);
        properties.put("end", end);
        properties.put("debounce", debounce);
        getElement().callFunction("setProperties", properties);
    }
}
