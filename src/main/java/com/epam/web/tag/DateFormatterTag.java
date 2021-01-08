package com.epam.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatterTag extends TagSupport {
    private static final SimpleDateFormat MATCH_FORMATTER = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private static final SimpleDateFormat HTML_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
    private static final String HTML_FORMAT = "HTML";
    private static final String MATCH_FORMAT = "MATCH";

    private String formatType;
    private Date date;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter jspWriter = pageContext.getOut();
        String formattedDate;
        switch (formatType.toUpperCase()) {
            case HTML_FORMAT:
                formattedDate = HTML_FORMATTER.format(date);
                break;
            case MATCH_FORMAT:
                formattedDate = MATCH_FORMATTER.format(date);
                break;
            default:
                throw new IllegalArgumentException("Invalid date format.");
        }
        writeFormattedDate(jspWriter, formattedDate);
        return SKIP_BODY;
    }

    private void writeFormattedDate(JspWriter jspWriter, String formattedDate) throws JspException {
        try {
            jspWriter.write(formattedDate);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }
}
