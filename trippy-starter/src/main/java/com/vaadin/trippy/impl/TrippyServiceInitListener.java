package com.vaadin.trippy.impl;

import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.ServiceInitEvent;
import com.vaadin.server.VaadinServiceInitListener;

/**
 * Various hacks that should really be features in Flow instead
 */
public class TrippyServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        ValoDependencyFilter dependencyFilter = new ValoDependencyFilter();
        if (dependencyFilter.hasValoWebJar()) {
            event.addDependencyFilter(dependencyFilter);
        }

        event.addBootstrapListener(new BootstrapListener() {
            @Override
            public void modifyBootstrapPage(BootstrapPageResponse response) {
                response.getDocument().head().appendElement("meta")
                        .attr("name", "viewport")
                        .attr("content", "width=device-width, initial-scale=1");
            }
        });
    }

}
