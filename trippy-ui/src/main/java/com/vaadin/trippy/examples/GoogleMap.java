package com.vaadin.trippy.examples;

import com.vaadin.ui.Component;
import com.vaadin.ui.Tag;
import com.vaadin.ui.common.HasSize;
import com.vaadin.ui.common.HtmlImport;

@Tag("google-map")
@HtmlImport("bower_components/google-map/google-map.html")
public class GoogleMap extends Component implements HasSize {

    public GoogleMap() {
        setWidth("700px");
        setHeight("500px");
    }

    public void setLocation(String lat, String lon) {
        getElement().setProperty("latitude", lat);
        getElement().setProperty("longitude", lon);
    }
}
