package com.vaadin.trippy.impl;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletConfiguration;

/**
 * The main servlet for the application.
 */
@WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true, initParams = {
        // Use "new" default for the frontend location
        @WebInitParam(name = "frontend.url.es6", value = "context://frontend/"),
        @WebInitParam(name = "polyfillBase", value = "frontend://bower_components/webcomponentsjs/") })
@VaadinServletConfiguration(productionMode = false, usingNewRouting = true)
public class Servlet extends VaadinServlet {
}
