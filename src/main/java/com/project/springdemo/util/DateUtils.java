package com.project.springdemo.util;

import com.project.springdemo.util.date.PersianDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    /**
     *
     */
    public static final Logger log = LogManager.getLogger(DateUtils.class);

    /**
     *
     */
    public static final String ENGLISH_LANGUAGE = "en";

    /**
     *
     */
    public static final String PERSIAN_DATE_FORMAT = "yyyy/MM/dd";

    /**
     *
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

    /**
     *
     */
    public static final String FARSI_LANGUAGE = "fa";

    /**
     *
     */
    public static final Locale FARSI_LOCALE = new Locale(FARSI_LANGUAGE);

    /**
     *
     */
    public static final Locale ENGLISH_LOCALE = new Locale(ENGLISH_LANGUAGE);

    public static String getLocaleDate(Locale locale, Date date, String format, boolean translate) {
        DateFormat dateFormat;

        if (locale != null && FARSI_LOCALE.getLanguage().equals(locale.getLanguage())) {
            dateFormat = new PersianDateFormat((format == null) ? PERSIAN_DATE_FORMAT : format);
        } else {
            dateFormat = new SimpleDateFormat((format == null) ? DEFAULT_DATE_FORMAT : format, Locale.getDefault());
        }

        String result = translate ? StringUtils.getLocaleText(locale, dateFormat.format(date)) : dateFormat.format(date);

        if (locale != null && FARSI_LOCALE.getLanguage().equals(locale.getLanguage())) {
            if (format != null && !format.startsWith("yyyy") && format.startsWith("yy")) {
                result = result.substring(2);
            }
        }

        return result;

    }
}
