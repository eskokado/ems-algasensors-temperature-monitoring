package com.eskcti.algasensors.temperature.monitoring.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;

import com.eskcti.algasensors.temperature.monitoring.domain.model.SensorId;
import com.eskcti.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.eskcti.algasensors.temperature.monitoring.domain.model.TemperatureLogId;

public interface TemperatureLogRepository extends JpaRepository<TemperatureLog, TemperatureLogId> {

    Page<TemperatureLog> findAllBySensorId(SensorId sensorId, Pageable pageable);
    

}
