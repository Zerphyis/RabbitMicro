package dev.Zerphyis.microRabbitMq.Application.services;


import dev.Zerphyis.microRabbitMq.Domain.model.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.ProductRepository;
import dev.Zerphyis.microRabbitMq.Domain.spefication.ProductSpecification;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.InvalidOperationException;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final ProductServiceRabbit rabbitService;

    public ProductService(ProductRepository repository, ProductServiceRabbit rabbitService) {
        this.repository = repository;
        this.rabbitService = rabbitService;
    }

    public Product create(Product product) {
        validateProduct(product);
        Product saved = repository.save(product);

        rabbitService.sendMenssage("product.created");

        return saved;
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

        rabbitService.sendMenssage("product.deleted");
    }

    public Page<Product> getProducts(
            String name,
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size,
            String sortBy,
            String direction) {

        String sortField = (sortBy == null || sortBy.isBlank()) ? "name" : sortBy;
        Sort.Direction sortDirection;
        try {
            sortDirection = (direction == null || direction.isBlank())
                    ? Sort.Direction.ASC
                    : Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException e) {
            sortDirection = Sort.Direction.ASC;
        }

        Sort sort = Sort.by(sortDirection, sortField);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return repository.findAllWithFilters(name, category, minPrice, maxPrice, pageRequest);
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
