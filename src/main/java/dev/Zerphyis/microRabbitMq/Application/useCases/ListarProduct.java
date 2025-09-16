package dev.Zerphyis.microRabbitMq.Application.useCases;

import dev.Zerphyis.microRabbitMq.Application.dto.ProductResponseDto;
import dev.Zerphyis.microRabbitMq.Application.mapper.ProductMapper;
import dev.Zerphyis.microRabbitMq.Domain.repository.ProductRepository;

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
