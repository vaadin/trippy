package com.vaadin.trippy.impl;

import java.util.stream.Stream;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.flow.di.DefaultInstantiator;
import com.vaadin.flow.di.Instantiator;
import com.vaadin.router.event.NavigationEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServiceInitListener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.common.HasElement;
import com.vaadin.util.ReflectTools;

public class SpringInstantiator implements Instantiator {

    private WebApplicationContext applicationContext;

    @Override
    public boolean init(VaadinService service) {
        VaadinServlet servlet = ((VaadinServletService) service).getServlet();
        applicationContext = WebApplicationContextUtils
                .findWebApplicationContext(servlet.getServletContext());

        return true;
    }

    @Override
    public Stream<VaadinServiceInitListener> getServiceInitListeners() {
        return DefaultInstantiator.getServiceLoaderListeners(
                SpringInstantiator.class.getClassLoader());
    }

    @Override
    public <T extends HasElement> T createRouteTarget(Class<T> routeTargetType,
            NavigationEvent event) {
        T instance = ReflectTools.createInstance(routeTargetType);
        AutowireCapableBeanFactory factory = applicationContext
                .getAutowireCapableBeanFactory();
        factory.autowireBean(instance);
        factory.initializeBean(instance, routeTargetType.getName());
        return instance;
    }

}
