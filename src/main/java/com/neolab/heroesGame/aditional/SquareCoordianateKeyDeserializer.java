package com.neolab.heroesGame.aditional;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.neolab.heroesGame.arena.SquareCoordinate;

import java.io.IOException;
import java.nio.ByteBuffer;

public class SquareCoordianateKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, SquareCoordinate.class);
    }
}
