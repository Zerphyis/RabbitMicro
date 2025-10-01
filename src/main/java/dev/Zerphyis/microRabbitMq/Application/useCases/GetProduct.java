package dev.Zerphyis.microRabbitMq.Application.useCases;

import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.ProductNotFoundException;

import java.util.UUID;

public class GetProduct {

    private final ProductRepository repository;

    public GetProduct(ProductRepository repository) {
        this.repository = repository;
    }

    public Product getById(UUID id) {
        return repository.findByid(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto com id " + id + " não encontrado"));
    }
}
