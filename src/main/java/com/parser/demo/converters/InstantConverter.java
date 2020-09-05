package com.parser.demo.converters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

@Component
public class InstantConverter implements Converter<String, Instant> {

    @Value("${weather.api.date-format}")
    private String format;

    @Override
    public Instant convert(String s) {
        try {
            return new SimpleDateFormat(format).parse(s).toInstant();
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong date format: " + s, e);
        }
    }
}
