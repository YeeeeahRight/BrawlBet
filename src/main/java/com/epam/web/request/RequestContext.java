package com.epam.web.request;

import java.util.Map;
import java.util.Set;

public class RequestContext {
    private final Map<String, Object> requestAttributes;
    private final Map<String, Object> sessionAttributes;
    private final Map<String, String[]> requestParameters;

    public RequestContext(Map<String, Object> requestAttributes,
                          Map<String, String[]> requestParameters,
                          Map<String, Object> sessionAttributes) {
        this.requestAttributes = requestAttributes;
        this.requestParameters = requestParameters;
        this.sessionAttributes = sessionAttributes;
    }

    public String getRequestParameter(String parameterName) {
        return requestParameters.get(parameterName)[0];
    }

    public void addAttribute(String attributeName, Object attributeContent) {
        requestAttributes.put(attributeName, attributeContent);
    }

    public Set<String> getRequestAttributeNames() {
        return requestAttributes.keySet();
    }

    public Object getRequestAttribute(String attributeName) {
        return requestAttributes.get(attributeName);
    }

    public void addSession(String attributeName, Object attributeContent) {
        sessionAttributes.put(attributeName, attributeContent);
    }

    public Set<String> getSessionAttributeNames() {
        return sessionAttributes.keySet();
    }

    public Object getSessionAttribute(String attributeName) {
        return sessionAttributes.get(attributeName);
    }
}
