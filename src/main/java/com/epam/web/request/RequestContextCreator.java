package com.epam.web.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestContextCreator {

    public RequestContext create(HttpServletRequest request) {
        Map<String, Object> attributes = new HashMap<>();
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            Object attribute = request.getAttribute(attributeName);
            attributes.put(attributeName, attribute);
        }

        Map<String, String[]> parameters = request.getParameterMap();

        Map<String, Object> sessionAttributes = new HashMap<>();
        HttpSession session = request.getSession();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String sessionName = sessionAttributeNames.nextElement();
            Object sessionAttribute = session.getAttribute(sessionName);
            sessionAttributes.put(sessionName, sessionAttribute);
        }

        return new RequestContext(attributes, parameters, sessionAttributes);
    }
}
