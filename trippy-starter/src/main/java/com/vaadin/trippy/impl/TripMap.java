package com.vaadin.trippy.impl;

import org.springframework.beans.factory.annotation.Value;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.trippy.data.Trip;

import elemental.json.Json;
import elemental.json.JsonObject;

@HtmlImport("DirectionSearch.html")
@Tag("direction-search")
@UIScope
@SpringComponent
public class TripMap extends Component {
    public TripMap(@Value("${map.apikey:}") String apiKey) {
        if (apiKey.isEmpty()) {
            throw new RuntimeException(
                    "Configure a map.apikey in your application.properties");
        }

        getElement().setProperty("apiKey", apiKey);
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
