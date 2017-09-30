package com.vaadin.trippy;

import com.vaadin.flow.dom.Element;
import com.vaadin.router.Route;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.html.Div;

@Route("map")
@HtmlImport("bower_components/google-map/google-map.html")
public class MapView extends Div {
    public MapView() {
        setSizeFull();

        Element map = new Element("google-map");
        map.getStyle().set("height", "100%").set("width", "100%");

        getElement().appendChild(map);
    }
}
