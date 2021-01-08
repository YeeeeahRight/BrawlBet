package com.epam.web.tag;

import com.epam.web.constant.Attribute;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;

public class AccessTag extends TagSupport {
    private static final String GUEST = "GUEST";
    private static final String NOT_GUEST = "NOT_GUEST";

    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        String role = (String) session.getAttribute(Attribute.ROLE);
        if (role == null) {
            if (this.role.equalsIgnoreCase(GUEST)) {
                return EVAL_BODY_INCLUDE;
            }
        } else if (role.equalsIgnoreCase(this.role) || NOT_GUEST.equalsIgnoreCase(this.role)) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}