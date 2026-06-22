package com.eskcti.algasensors.temperature.monitoring.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorId;

public interface SensorAlertRepository extends JpaRepository<SensorAlert, SensorId> {
    Optional<SensorAlert> findById(SensorId sensorId);
    SensorAlert saveAndFlush(SensorAlert sensorAlert);
    void deleteById(SensorId sensorId);
}
