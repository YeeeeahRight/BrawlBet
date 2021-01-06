package com.epam.web.controller.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Set;

public class RequestFiller {
    public void fillData(HttpServletRequest request, RequestContext requestContext) {
        removeRequestAttributes(request);
        Set<String> requestAttributeNames = requestContext.getRequestAttributeNames();
        for (String attributeName : requestAttributeNames) {
            Object attributeValue = requestContext.getRequestAttribute(attributeName);
            request.setAttribute(attributeName, attributeValue);
        }

        removeSessionAttributes(request);
        Set<String> sessionAttributeNames = requestContext.getSessionAttributeNames();
        HttpSession session = request.getSession();
        for (String attributeName : sessionAttributeNames) {
            Object attributeValue = requestContext.getSessionAttribute(attributeName);
            session.setAttribute(attributeName, attributeValue);
        }
    }

    private void removeSessionAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> sessionAttributeNames = session.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String attributeName = sessionAttributeNames.nextElement();
            session.removeAttribute(attributeName);
        }
    }

    private void removeRequestAttributes(HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            request.removeAttribute(attributeName);
        }
    }
}
