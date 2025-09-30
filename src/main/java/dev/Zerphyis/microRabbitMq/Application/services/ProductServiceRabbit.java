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

    @Value("${rabbitmq.queue}")
    private String productQueue;

    @Value("${rabbitmq.exchange:}")
    private String exchange;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceRabbit.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ProductServiceRabbit(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
    }


    public void sendMessage(Object message) {
        try {
            String json = objectMapper.writeValueAsString(message);

            if (exchange == null || exchange.isBlank()) {
                rabbitTemplate.convertAndSend(productQueue, json);
            } else {
                rabbitTemplate.convertAndSend(exchange, productQueue, json);
            }

            logger.info("Mensagem enviada para [{}] com sucesso: {}", productQueue, json);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao converter objeto para JSON: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Falha ao converter objeto para JSON", e);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem para RabbitMQ: {}", e.getMessage(), e);
            throw new IllegalStateException("Falha ao enviar mensagem para RabbitMQ", e);
        }
    }
}
