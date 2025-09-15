package dev.Zerphyis.microRabbitMq.Application.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceRabbit {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue}")
    private String productqueue;

    public ProductServiceRabbit(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMenssage(String message){
        rabbitTemplate.convertAndSend(productqueue,message);
        System.out.println("Mensagem enviada para fila [" + productqueue + "]: " + message);
    }
}
