package dev.Zerphyis.microRabbitMq.Application.useCases.products;

import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Application.mapper.product.ProductMapper;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;

public class CriarProduct {
    private final ProductRepository productRepository;

    public CriarProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product execute(ProductRequestDto dto) {
        Product product = ProductMapper.toEntity(dto);
        return productRepository.save(product);
    }
}
