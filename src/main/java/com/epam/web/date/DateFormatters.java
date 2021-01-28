package com.epam.web.date;

import java.text.SimpleDateFormat;

public final class DateFormatters {
    public static final SimpleDateFormat RU_LOCALE_FORMATTER = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static final SimpleDateFormat ENG_LOCALE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    public static final SimpleDateFormat HTML_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    public static final SimpleDateFormat MYSQL_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
