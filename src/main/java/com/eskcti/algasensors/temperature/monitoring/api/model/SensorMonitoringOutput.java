package com.eskcti.algasensors.temperature.monitoring.api.model;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorMonitoringOutput {
    // id is sent as the TSID string value
    private String id;
    private Double lastTemperature;
    private OffsetDateTime updatedAt;
    private Boolean enabled;
}
