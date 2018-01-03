package com.vaadin.trippy;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.trippy.impl.TripMap;

@StyleSheet("styles.css")
public class MainLayout extends Div implements RouterLayout {
    public MainLayout() {
        add(TripMap.getCurrent());
    }
}
