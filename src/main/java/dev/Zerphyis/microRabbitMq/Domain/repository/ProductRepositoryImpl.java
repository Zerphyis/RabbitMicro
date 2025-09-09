package dev.Zerphyis.microRabbitMq.Domain.repository;

import dev.Zerphyis.microRabbitMq.Domain.model.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.jpa.ProductRepositoryJpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductRepositoryImpl implements ProductRepository{

    private final ProductRepositoryJpa jpaRepository;

    public ProductRepositoryImpl(ProductRepositoryJpa jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByid(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void deleteId(UUID id) {
        jpaRepository.deleteById(id);
    }
}
