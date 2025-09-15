package dev.Zerphyis.microRabbitMq.Infra.controllers;

import dev.Zerphyis.microRabbitMq.Application.services.ProductServiceRabbit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProductProducerController {
    private final ProductServiceRabbit serviceRabbit;

    public ProductProducerController(ProductServiceRabbit serviceRabbit) {
        this.serviceRabbit = serviceRabbit;
    }

    @PostMapping("/send")
    public String sendProduct(@RequestBody String productJson) {
        serviceRabbit.sendMenssage(productJson);
        return "Produto enviado para fila!";
    }
}
