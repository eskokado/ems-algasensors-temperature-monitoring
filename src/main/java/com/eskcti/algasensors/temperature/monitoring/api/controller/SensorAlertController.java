package com.eskcti.algasensors.temperature.monitoring.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.eskcti.algasensors.temperature.monitoring.api.model.SensorAlertInput;
import com.eskcti.algasensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorId;
import com.eskcti.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;

import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
@RequiredArgsConstructor
public class SensorAlertController {
    private final SensorAlertRepository sensorAlertRepository;

    @GetMapping
    public SensorAlertOutput getById(@PathVariable("sensorId") TSID sensorId) {
        var sensorAlert = sensorAlertRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return SensorAlertOutput.builder()
                .id(sensorAlert.getId().getValue())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }

    @PutMapping
    public SensorAlertOutput createOrUpdate(@PathVariable("sensorId") TSID sensorId,
            @RequestBody SensorAlertInput input) {
        var id = new SensorId(sensorId);
        var sensorAlert = sensorAlertRepository.findById(id)
                .map(existing -> {
                    existing.setMaxTemperature(input.getMaxTemperature());
                    existing.setMinTemperature(input.getMinTemperature());
                    return existing;
                })
                .orElseGet(() -> SensorAlert.builder()
                        .id(id)
                        .maxTemperature(input.getMaxTemperature())
                        .minTemperature(input.getMinTemperature())
                        .build());

        sensorAlert = sensorAlertRepository.saveAndFlush(sensorAlert);

        return SensorAlertOutput.builder()
                .id(sensorAlert.getId().getValue())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("sensorId") TSID sensorId) {
        var id = new SensorId(sensorId);
        if (sensorAlertRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        sensorAlertRepository.deleteById(id);
    }
}
