package com.epam.web.date;

import java.util.Date;

public class DateFormatter {
    private final Date date;

    public DateFormatter(Date date) {
        this.date = date;
    }

    public String format(DateFormatType dateFormatType) {
        switch (dateFormatType) {
            case MYSQL:
                return DateFormatters.MYSQL_FORMATTER.format(date);
            case HTML:
                return DateFormatters.HTML_FORMATTER.format(date);
            case MATCH:
                return DateFormatters.MATCH_FORMATTER.format(date);
            default:
                throw new IllegalArgumentException("Unknown date format type.");
        }
    }
}
