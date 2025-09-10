package dev.Zerphyis.microRabbitMq.Application.services;


import dev.Zerphyis.microRabbitMq.Domain.model.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.ProductRepository;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.InvalidOperationException;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product product) {
        validateProduct(product);
        return repository.save(product);
    }

    public Product getById(UUID id) {
        return repository.findByid(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto com id " + id + " não encontrado"));
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public void delete(UUID id) {
        Product product = repository.findByid(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto com id " + id + " não encontrado"));
        repository.deleteId(product.getId());
    }

    private void validateProduct(Product product) {
        if (product.getPrice() == null || product.getPrice().signum() < 0) {
            throw new InvalidOperationException("Preço do produto não pode ser nulo ou negativo.");
        }
        if (product.getStock() == null || product.getStock() < 0) {
            throw new InvalidOperationException("Estoque do produto não pode ser nulo ou negativo.");
        }
        if (product.getName() == null || product.getName().isBlank()) {
            throw new InvalidOperationException("Produto deve ter um nome válido.");
        }
    }
}
