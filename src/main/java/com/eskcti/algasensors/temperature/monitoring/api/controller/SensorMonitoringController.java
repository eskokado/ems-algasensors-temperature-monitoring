package com.eskcti.algasensors.temperature.monitoring.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.eskcti.algasensors.temperature.monitoring.api.model.SensorMonitoringOutput;
import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorId;
import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.eskcti.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;

import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoringController {
    private final SensorMonitoringRepository sensorMonitoringRepository;

    @GetMapping
    public SensorMonitoringOutput getById(@PathVariable("sensorId") TSID sensorId) {
        var sensorMonitoring = findByIdOrDefault(sensorId);
        
        return SensorMonitoringOutput.builder()
            .id(sensorMonitoring.getId().getValue())
            .lastTemperature(sensorMonitoring.getLastTemperature())
            .updatedAt(sensorMonitoring.getUpdatedAt())
            .enabled(sensorMonitoring.getEnabled())
            .build();
    }

    private SensorMonitoring findByIdOrDefault(TSID sensorId) {
        return sensorMonitoringRepository.findById(new SensorId(sensorId))
                .orElse(SensorMonitoring.builder()
                    .id(new SensorId(sensorId))
                    .lastTemperature(null)
                    .updatedAt(null)
                    .enabled(false)
                    .build());
    }

    @PutMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableSensor(@PathVariable("sensorId") TSID sensorId) {
        var sensorMonitoring = findByIdOrDefault(sensorId);
        sensorMonitoring.setEnabled(true);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }

    @DeleteMapping("/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableSensor(@PathVariable("sensorId") TSID sensorId)
    {
        var sensorMonitoring = findByIdOrDefault(sensorId);
        sensorMonitoring.setEnabled(false);
        sensorMonitoringRepository.saveAndFlush(sensorMonitoring);
    }
}
