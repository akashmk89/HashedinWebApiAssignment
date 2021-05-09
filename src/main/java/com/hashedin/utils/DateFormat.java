package com.hashedin.utils;

import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormat {
    @Bean
    public Date getDate(String token) throws ParseException {
        Date date = new Date();
        java.text.DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        date = token.isEmpty()? null: format.parse(token.replaceAll("\"","").trim());
        return date;
    }
}
