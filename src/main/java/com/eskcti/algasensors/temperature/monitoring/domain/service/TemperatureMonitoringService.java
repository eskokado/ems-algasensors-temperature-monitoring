package com.eskcti.algasensors.temperature.monitoring.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.eskcti.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorId;
import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.eskcti.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.eskcti.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.eskcti.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.eskcti.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class TemperatureMonitoringService {
    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    public void processTemperatureReading(TemperatureLogData temperatureLogData) {
        sensorMonitoringRepository.findById(new SensorId(temperatureLogData.getSensorId()))
            .ifPresentOrElse(
                sensor -> handleSensorMonitoring(temperatureLogData, sensor), 
                () -> logIgnoredTemperature(temperatureLogData) 
            );
    }

    private void logIgnoredTemperature(TemperatureLogData temperatureLogData) {
        log.info("Temperature Ignored: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
    }

    private void handleSensorMonitoring(TemperatureLogData temperatureLogData, SensorMonitoring sensor) {
        if (sensor.getEnabled()) {
            sensor.setLastTemperature(temperatureLogData.getValue());
            sensor.setUpdatedAt(OffsetDateTime.now());
            sensorMonitoringRepository.save(sensor);

            TemperatureLog temperatureLog = TemperatureLog
                .builder()
                .id(new TemperatureLogId(temperatureLogData.getId()))
                .registeredAt(temperatureLogData.getRegisteredAt())
                .value(temperatureLogData.getValue())
                .sensorId(new SensorId(temperatureLogData.getSensorId()))
                .build();

            temperatureLogRepository.save(temperatureLog);
            log.info("Temperature Updated: SensorId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
        } else {
            logIgnoredTemperature(temperatureLogData);
        }
    }
}
