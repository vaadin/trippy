package com.vaadin.trippy.impl;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.router.Route;
import com.vaadin.server.InvalidRouteConfigurationException;
import com.vaadin.server.startup.RouteRegistry;
import com.vaadin.ui.Component;

@org.springframework.stereotype.Component
public class BootWorkarounds implements ServletContextInitializer {
    @Autowired
    private WebApplicationContext context;

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        if (!(context instanceof EmbeddedWebApplicationContext)) {
            // Will be handled by SCI in non-embedded cases
            return;
        }

        RouteRegistry registry = RouteRegistry.getInstance(servletContext);

        // Explicitly scan for for and register navigation targets since the
        // automatic registration is disabled when running through Boot
        Set<Class<? extends Component>> navigationTargets = findNavigationTargets();

        try {
            registry.setNavigationTargets(navigationTargets);
        } catch (InvalidRouteConfigurationException e) {
            throw new ServletException(e);
        }
    }

    private Set<Class<? extends Component>> findNavigationTargets()
            throws ServletException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
                false);
        scanner.setResourceLoader(context);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Route.class));
        Set<BeanDefinition> candidateComponents = scanner
                .findCandidateComponents("com.vaadin.trippy");

        Set<Class<? extends Component>> routeBeans = new HashSet<>();
        for (BeanDefinition candidateComponent : candidateComponents) {
            AbstractBeanDefinition definition = (AbstractBeanDefinition) candidateComponent;
            Class<?> beanClass;
            if (definition.hasBeanClass()) {
                beanClass = definition.getBeanClass();
            } else {
                try {
                    beanClass = definition
                            .resolveBeanClass(getClass().getClassLoader());
                } catch (ClassNotFoundException e) {
                    throw new ServletException(e);
                }
            }
            routeBeans.add(beanClass.asSubclass(Component.class));
        }
        return routeBeans;
    }

}
