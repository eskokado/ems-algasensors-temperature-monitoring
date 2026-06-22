package com.eskcti.algasensors.temperature.monitoring.domain.model;

import java.time.OffsetDateTime;

import javax.annotation.processing.SupportedAnnotationTypes;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TemperatureLog {

    @Id
    @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "UUID"))
    private TemperatureLogId id;

    @Column(name = "\"value\"")
    private Double value;

    private OffsetDateTime registeredAt;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "sensor_id", columnDefinition = "BIGINT"))
    private SensorId sensorId;
}
