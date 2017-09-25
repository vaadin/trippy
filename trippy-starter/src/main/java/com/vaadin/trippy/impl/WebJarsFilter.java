package com.vaadin.trippy.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.webjars.WebJarAssetLocator;

import elemental.json.Json;
import elemental.json.JsonObject;

/**
 * Servlet filter that modifies requests for files in /fontend/bower_components/
 * to instead use the corresponding webjars location. This filter would be part
 * of Flow and part of the application itself.
 */
@WebFilter(urlPatterns = WebJarsFilter.PATTERN)
public class WebJarsFilter implements Filter {

    private static final String PREFIX = "/frontend/bower_components/";

    protected static final String PATTERN = PREFIX + "*";

    private WebJarAssetLocator locator;

    private Map<String, String> nameToWebJarAndVersion = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        locator = new WebJarAssetLocator();

        /*
         * Look for a bower.json in each webJar to find out the
         * bower_components/<name>/ path that that corresponds to that webJar
         */
        locator.getWebJars().forEach((webJar, version) -> {
            String bowerName = getBowerName(webJar);
            if (bowerName == null) {
                // Not a bower webjar. Ignore.
            }

            String webJarAndVersion = webJar + "/" + version;
            String oldWebJarAndVersion = nameToWebJarAndVersion.put(bowerName,
                    webJarAndVersion);

            if (oldWebJarAndVersion != null) {
                String normalizedVersion = normalizeVersion(version);
                String oldVersion = normalizeVersion(oldWebJarAndVersion
                        .substring(oldWebJarAndVersion.lastIndexOf('/') + 1));

                if (!normalizedVersion.equals(oldVersion)) {
                    throw new IllegalStateException(
                            "There are multiple web jars with different versions for the bower module "
                                    + bowerName + ": " + webJarAndVersion + ", "
                                    + oldWebJarAndVersion);
                }
            }
        });
    }

    private static String normalizeVersion(String version) {
        if (version.matches("v\\d+\\.\\d+\\.\\d+(-.*)?")) {
            return version.substring(1);
        } else {
            return version;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        String webJarPath = getWebJarPath(servletRequest.getPathInfo());

        if (webJarPath != null) {
            // Use a request with a pathInfo that points to the webjar
            request = new HttpServletRequestWrapper(servletRequest) {
                @Override
                public String getPathInfo() {
                    return webJarPath;
                }
            };
        }

        chain.doFilter(request, response);
    }

    private String getWebJarPath(String path) {
        if (!path.startsWith(PREFIX)) {
            return null;
        }
        path = path.substring(PREFIX.length());

        int separatorIndex = path.indexOf('/');
        if (separatorIndex < 0) {
            return null;
        }

        String bowerModule = path.substring(0, separatorIndex);
        String webJarAndVersion = nameToWebJarAndVersion.get(bowerModule);

        if (webJarAndVersion == null) {
            return null;
        }

        String fileName = path.substring(separatorIndex);
        return "/webjars/" + webJarAndVersion + fileName;
    }

    private String getBowerName(String webJar) {
        String bowerJsonPath = locator.getFullPathExact(webJar, "bower.json");
        if (bowerJsonPath == null) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader()
                        .getResourceAsStream(bowerJsonPath)))) {
            // Ignoring linebreaks
            String jsonString = reader.lines().collect(Collectors.joining());

            JsonObject json = Json.parse(jsonString);

            return json.getString("name");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void destroy() {
        // nop
    }

}
