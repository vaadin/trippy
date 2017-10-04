package com.vaadin.trippy;

import com.vaadin.router.RouterLayout;
import com.vaadin.trippy.impl.DirectionSearch;
import com.vaadin.ui.common.StyleSheet;
import com.vaadin.ui.html.Div;

@StyleSheet("styles.css")
public class MainLayout extends Div implements RouterLayout {
    public MainLayout() {
        add(DirectionSearch.getCurrent());
    }
}
