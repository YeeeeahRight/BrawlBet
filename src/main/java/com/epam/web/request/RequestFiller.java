package com.epam.web.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class RequestFiller {
    public void fillData(HttpServletRequest request, RequestContext requestContext) {
        Set<String> requestAttributeNames = requestContext.getRequestAttributeNames();
        for (String attributeName : requestAttributeNames) {
            Object attributeValue = requestContext.getRequestAttribute(attributeName);
            request.setAttribute(attributeName, attributeValue);
        }

        Set<String> sessionAttributeNames = requestContext.getSessionAttributeNames();
        HttpSession session = request.getSession();
        for (String attributeName : sessionAttributeNames) {
            Object attributeValue = requestContext.getSessionAttribute(attributeName);
            session.setAttribute(attributeName, attributeValue);
        }
    }

}
