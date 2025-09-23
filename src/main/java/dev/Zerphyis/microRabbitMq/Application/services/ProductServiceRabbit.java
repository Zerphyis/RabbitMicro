package dev.Zerphyis.microRabbitMq.Application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceRabbit {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceRabbit.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.queue}")
    private String productQueue;

    public ProductServiceRabbit(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }


    public void sendMessage(Object message) {
        try {
            String json = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(productQueue, json);
            logger.info("Mensagem enviada para fila [{}]: {}", productQueue, json);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao converter objeto para JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao enviar mensagem para a fila RabbitMQ", e);
        }
    }
}
