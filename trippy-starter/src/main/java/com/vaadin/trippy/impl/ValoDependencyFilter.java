package com.vaadin.trippy.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.webjars.WebJarAssetLocator;

import com.vaadin.server.DependencyFilter;
import com.vaadin.server.ServiceInitEvent;
import com.vaadin.server.VaadinServiceInitListener;
import com.vaadin.shared.ui.Dependency;
import com.vaadin.shared.ui.Dependency.Type;

public class ValoDependencyFilter implements VaadinServiceInitListener {

    private static final String VALO_WEBJAR = "github-com-vaadin-vaadin-valo-theme";

    private final WebJarAssetLocator locator = new WebJarAssetLocator();

    @Override
    public void serviceInit(ServiceInitEvent event) {
        if (locator.getWebJars().containsKey(VALO_WEBJAR)) {
            event.addDependencyFilter(new DependencyFilter() {
                @Override
                public List<Dependency> filter(List<Dependency> dependencies,
                        FilterContext filterContext) {
                    return dependencies.stream().flatMap(
                            ValoDependencyFilter.this::expandThemeDependency)
                            .collect(Collectors.toList());
                }
            });
        }
    }

    private Stream<Dependency> expandThemeDependency(Dependency dependency) {
        Dependency themeDependency = getThemeDependency(dependency);
        if (themeDependency != null) {
            return Stream.of(themeDependency, dependency);
        } else {
            return Stream.of(dependency);
        }
    }

    private Dependency getThemeDependency(Dependency dependency) {
        if (dependency.getType() != Type.HTML_IMPORT) {
            return null;
        }

        String url = dependency.getUrl();
        int lastSegmentStart = url.lastIndexOf('/');
        if (lastSegmentStart == -1) {
            return null;
        }
        String lastSegment = url.substring(lastSegmentStart + 1);

        String fullPathExact = locator.getFullPathExact(VALO_WEBJAR,
                lastSegment);
        if (fullPathExact == null) {
            return null;
        } else {
            return new Dependency(Type.HTML_IMPORT,
                    "frontend://bower_components/vaadin-valo-theme/"
                            + lastSegment,
                    dependency.getLoadMode());
        }
    }

}