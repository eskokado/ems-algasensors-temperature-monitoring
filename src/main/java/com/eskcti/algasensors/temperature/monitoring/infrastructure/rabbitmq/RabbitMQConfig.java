package com.eskcti.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_PROCESS_TEMPERATURE = "temperature-monitoring.process-temperature.v1.q";
    public static final String QUEUE_ALERTING = "temperature-monitoring.alerting.v1.q";
    
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queueProcessTemperature() {
        return QueueBuilder
            .durable(QUEUE_PROCESS_TEMPERATURE)
            .build();
    }

    @Bean
    public Queue queueAlerting() {
        return QueueBuilder
            .durable(QUEUE_ALERTING)
            .build();
    }

    public FanoutExchange exchange() {
        return ExchangeBuilder
            .fanoutExchange("temperature-processing.temperature-received.v1.e")
            .build();
    }

    @Bean
    public Binding binding_process_temperature() {
        return BindingBuilder.bind(queueProcessTemperature()).to(exchange());
    }
    
    @Bean
    public Binding binding_alerting() {
        return BindingBuilder.bind(queueAlerting()).to(exchange());
    }
}
