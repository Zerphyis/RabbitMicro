package dev.Zerphyis.microRabbitMq.Application.useCases;

import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;

public class FilterGetProduct {

    private final ProductRepository repository;

    public FilterGetProduct(ProductRepository repository) {
        this.repository = repository;
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
}
