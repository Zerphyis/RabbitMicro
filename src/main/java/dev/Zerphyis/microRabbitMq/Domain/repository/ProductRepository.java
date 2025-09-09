package dev.Zerphyis.microRabbitMq.Domain.repository;

import dev.Zerphyis.microRabbitMq.Domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product>  findByid(UUID uuid);
    List<Product> findAll();
    void deleteId(UUID uuid);
}
