package dev.Zerphyis.microRabbitMq.Infra.controllers;

import dev.Zerphyis.microRabbitMq.Application.dto.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Application.services.ProductServiceRabbit;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> sendProduct(@RequestBody ProductRequestDto productDTO) {
        System.out.println("Entrou no controller!");
        serviceRabbit.sendMessage(productDTO);
        return ResponseEntity.ok("Produto enviado para fila com sucesso!");
    }

}
