package com.eskcti.algasensors.temperature.monitoring.domain.service;

import org.springframework.stereotype.Service;

import com.eskcti.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorId;
import com.eskcti.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SensorAlertService {
    private final SensorAlertRepository sensorAlertRepository;

    @Transactional
    public void handleAlerting(TemperatureLogData temperatureLogData) {
        sensorAlertRepository.findById(new SensorId(temperatureLogData.getSensorId()))
            .ifPresentOrElse((alert) -> {
                if (
                    alert.getMaxTemperature() != null &&
                    temperatureLogData.getValue().compareTo(alert.getMaxTemperature()) >= 0
                ) {
                    log.info("Alert Max Temperature: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
                } else if (
                    alert.getMinTemperature() != null &&
                    temperatureLogData.getValue().compareTo(alert.getMinTemperature()) <= 0
                ) {
                    log.info("Alert Min Temperature: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
                } else {
                    logIgnoredAlert(temperatureLogData);
                }
            }, () -> {
                logIgnoredAlert(temperatureLogData);
            });
    }

    private void logIgnoredAlert(TemperatureLogData temperatureLogData) {
        log.info("Alert Ignored: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
    }

}
