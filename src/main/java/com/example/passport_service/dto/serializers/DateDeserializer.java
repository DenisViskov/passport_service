package com.example.passport_service.dto.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer extends StdDeserializer<Date> {

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    protected DateDeserializer(Class<?> vc) {
        super(vc);
    }

    @SneakyThrows
    @Override public Date deserialize(final JsonParser p, final DeserializationContext ctxt) {
        return formatter.parse(p.getText());
    }
}
