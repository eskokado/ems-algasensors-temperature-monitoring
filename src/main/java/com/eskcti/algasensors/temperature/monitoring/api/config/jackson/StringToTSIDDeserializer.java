package com.eskcti.algasensors.temperature.monitoring.api.config.jackson;

import io.hypersistence.tsid.TSID;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StringToTSIDDeserializer extends JsonDeserializer<TSID> {

    @Override
    public TSID deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        return TSID.from(p.getValueAsString());
    }

}
