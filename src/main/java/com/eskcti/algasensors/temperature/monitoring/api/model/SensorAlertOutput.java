package com.eskcti.algasensors.temperature.monitoring.api.model;

import lombok.Builder;
import lombok.Data;

import io.hypersistence.tsid.TSID;

@Data
@Builder
public class SensorAlertOutput {
    private TSID id;
    private Double maxTemperature;
    private Double minTemperature;
}
