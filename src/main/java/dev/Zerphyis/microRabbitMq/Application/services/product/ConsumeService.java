package dev.Zerphyis.microRabbitMq.Application.services.product;

import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductRequestDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ConsumeService {
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receiveProduct(@Payload ProductRequestDto dto) {
        System.out.println("📩 Produto recebido da fila → "
                + dto.getName() + " | preço: " + dto.getPrice());
    }
}
