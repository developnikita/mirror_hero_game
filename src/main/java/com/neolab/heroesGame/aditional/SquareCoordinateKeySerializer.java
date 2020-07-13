package com.neolab.heroesGame.aditional;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.neolab.heroesGame.arena.SquareCoordinate;

import java.io.IOException;
import java.util.Arrays;

public class SquareCoordinateKeySerializer extends JsonSerializer<SquareCoordinate> {

    @Override
    public void serialize(SquareCoordinate coordinate, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        jsonGenerator.writeFieldName(mapper.writeValueAsString(coordinate));
    }
}
