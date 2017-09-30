package com.vaadin.trippy.impl;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletConfiguration;

/**
 * The main servlet for the application.
 */
@WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true, initParams = {
        // Workaround for missing WebJars support in polyfill detection logic
        @WebInitParam(name = "polyfillBase", value = "frontend://bower_components/webcomponentsjs/") })
@VaadinServletConfiguration(productionMode = false, usingNewRouting = true)
public class Servlet extends VaadinServlet {
}
