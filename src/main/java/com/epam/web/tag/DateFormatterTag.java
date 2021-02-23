package com.epam.web.tag;

import com.epam.web.date.DateFormatters;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Date;

public class DateFormatterTag extends TagSupport {
    private static final String HTML_FORMAT = "HTML";
    private static final String ENG_FORMAT = "en_US";

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
        switch (formatType) {
            case HTML_FORMAT:
                formattedDate = DateFormatters.HTML_FORMATTER.format(date);
                break;
            case ENG_FORMAT:
                formattedDate = DateFormatters.ENG_LOCALE_FORMATTER.format(date);
                break;
            default:
                formattedDate = DateFormatters.RU_LOCALE_FORMATTER.format(date);
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
