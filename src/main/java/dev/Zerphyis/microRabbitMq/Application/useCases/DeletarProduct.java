package dev.Zerphyis.microRabbitMq.Application.useCases;

import dev.Zerphyis.microRabbitMq.Domain.repository.ProductRepository;

import java.util.UUID;

public class DeletarProduct {
    private final ProductRepository productRepository;

    public DeletarProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(UUID id) {
        productRepository.deleteId(id);
    }
}
