package com.vaadin.trippy;

import com.vaadin.router.RouterLayout;
import com.vaadin.ui.html.Div;

public class MainLayout extends Div implements RouterLayout {
    public MainLayout() {
        add(DirectionSearch.getCurrent());
    }
}
