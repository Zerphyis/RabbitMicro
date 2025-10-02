package dev.Zerphyis.microRabbitMq.Application.useCases.products;

import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;

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
