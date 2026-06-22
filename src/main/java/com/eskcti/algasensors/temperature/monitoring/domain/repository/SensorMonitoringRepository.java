package com.eskcti.algasensors.temperature.monitoring.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorId;
import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorMonitoring;

public interface SensorMonitoringRepository extends JpaRepository<SensorMonitoring, SensorId> {
    Optional<SensorMonitoring> findById(SensorId sensorId);
    SensorMonitoring saveAndFlush(SensorMonitoring sensorMonitoring);
}
