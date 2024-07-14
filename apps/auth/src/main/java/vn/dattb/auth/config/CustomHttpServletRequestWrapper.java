package vn.dattb.auth.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> customHeaders;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public void addHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        // Check the custom headers first
        String headerValue = customHeaders.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        // Else return from into the original wrapped request
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        // Create a set of header names from the original request
        Set<String> headerNames = new HashSet<>(Collections.list(((HttpServletRequest) getRequest()).getHeaderNames()));
        // Add the custom header names
        headerNames.addAll(customHeaders.keySet());
        return Collections.enumeration(headerNames);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (customHeaders.containsKey(name)) {
            return Collections.enumeration(Collections.singletonList(customHeaders.get(name)));
        }
        return ((HttpServletRequest) getRequest()).getHeaders(name);
    }
}