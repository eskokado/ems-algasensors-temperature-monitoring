package com.eskcti.algasensors.temperature.monitoring.api.model;

import java.time.OffsetDateTime;

import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorId;

import io.hypersistence.tsid.TSID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorMonitoringOutput {
    private TSID id;
    private Double lastTemperature;
    private OffsetDateTime updatedAt;
    private Boolean enabled;
}
