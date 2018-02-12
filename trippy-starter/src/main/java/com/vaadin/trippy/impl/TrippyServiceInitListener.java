package com.vaadin.trippy.impl;

import org.jsoup.nodes.Element;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.flow.server.BootstrapListener;
import com.vaadin.flow.server.BootstrapPageResponse;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinServletService;

/**
 * Various hacks that should really be features in Flow instead
 */
public class TrippyServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        VaadinServletService service = (VaadinServletService) event.getSource();

        WebApplicationContext applicationContext = WebApplicationContextUtils
                .findWebApplicationContext(
                        service.getServlet().getServletContext());

        String apiKey = applicationContext.getEnvironment()
                .getProperty("map.apikey");
        if (apiKey == null) {
            throw new RuntimeException(
                    "Configure a map.apikey in your application.properties");
        }

        event.addBootstrapListener(new BootstrapListener() {
            @Override
            public void modifyBootstrapPage(BootstrapPageResponse response) {
                Element head = response.getDocument().head();

                // Flow sets element properties too late for google-map to get
                // the right API key. As a temporary workaround, we put our key
                // in the map's prototype instead.
                head.appendElement("script").html("window.mapApiKey = '"
                        + apiKey + "';\n"
                        + "customElements.whenDefined('google-map').then(function() {customElements.get('google-map').prototype.apiKey = window.mapApiKey})");
            }
        });
    }

}
