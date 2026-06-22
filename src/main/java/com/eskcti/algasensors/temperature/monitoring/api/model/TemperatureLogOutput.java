package com.eskcti.algasensors.temperature.monitoring.api.model;

import java.util.UUID;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class TemperatureLogOutput {
    private UUID id;
    private TSID sensorId;
    private OffsetDateTime registeredAt;
    private Double value;
}
