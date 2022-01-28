package com.example.passport_service.dto.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateSerializer extends StdSerializer<Date> {

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    protected DateSerializer(Class<Date> t) {
        super(t);
    }

    @SneakyThrows
    @Override public void serialize(final Date value, final JsonGenerator gen, final SerializerProvider provider) {
        gen.writeString(formatter.format(value));
    }
}
