package com.vaadin.trippy;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.trippy.impl.TripMap;

@StyleSheet("styles.css")
@Theme(Lumo.class)
@Viewport("width=device-width, initial-scale=1")
public class MainLayout extends Div implements RouterLayout {
    public MainLayout() {
        add(TripMap.getCurrent());
    }
}
