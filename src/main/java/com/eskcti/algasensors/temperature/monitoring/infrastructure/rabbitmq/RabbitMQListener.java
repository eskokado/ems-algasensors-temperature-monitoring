package com.eskcti.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import java.time.Duration;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.eskcti.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.eskcti.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;

import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private final TemperatureMonitoringService temperatureMonitoringService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PROCESS_TEMPERATURE, concurrency = "2-3")
    @SneakyThrows
    public void handleTemperatureProcessing(
            @Payload TemperatureLogData temperatureLogData,
            @Headers Map<String, Object> headers) {
        temperatureMonitoringService.processTemperatureReading(temperatureLogData);
        Thread.sleep(Duration.ofSeconds(5));
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ALERTING, concurrency = "2-3")
    @SneakyThrows
    public void handleAlerting(
            @Payload TemperatureLogData temperatureLogData,
            @Headers Map<String, Object> headers) {
        log.info("Alerting: SensorId {} Temp {}", temperatureLogData.getSensorId().toString(), temperatureLogData.getValue());
        Thread.sleep(Duration.ofSeconds(5));
    }
}
