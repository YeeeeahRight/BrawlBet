package com.epam.web.date;

import java.text.ParseException;
import java.util.Date;

public class DateParser {
    private final String dateString;

    public DateParser(String dateString) {
        this.dateString = dateString;
    }

    public Date parse(DateFormatType dateFormatType) throws ParseException {
        switch (dateFormatType) {
            case MYSQL:
                return DateFormatters.MYSQL_FORMATTER.parse(dateString);
            case HTML:
                return DateFormatters.HTML_FORMATTER.parse(dateString);
            case LOCALE_RU:
                return DateFormatters.RU_LOCALE_FORMATTER.parse(dateString);
            case LOCALE_ENG:
                return DateFormatters.ENG_LOCALE_FORMATTER.parse(dateString);
            default:
                throw new IllegalArgumentException("Unknown date format type.");
        }
    }
}
