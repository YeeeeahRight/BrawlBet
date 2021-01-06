package com.epam.web.date;

import java.text.SimpleDateFormat;

final class DateFormatters {
    /*private-package*/ static final SimpleDateFormat MATCH_FORMATTER = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    /*private-package*/ static final SimpleDateFormat HTML_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
    /*private-package*/ static final SimpleDateFormat MYSQL_FORMATTER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
}
