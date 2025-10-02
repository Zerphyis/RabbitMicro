package dev.Zerphyis.microRabbitMq.Application.useCases.products;

import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductResponseDto;
import dev.Zerphyis.microRabbitMq.Application.mapper.product.ProductMapper;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ListarProduct {
    private final ProductRepository productRepository;

    public ListarProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> execute() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }
}
