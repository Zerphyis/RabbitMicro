package dev.Zerphyis.microRabbitMq.Application.useCases;

import dev.Zerphyis.microRabbitMq.Application.dto.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

public class AtualizarProduct {
    private final ProductRepository productRepository;

    public AtualizarProduct(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> execute(UUID id, ProductRequestDto dto) {
        Optional<Product> existingProduct = productRepository.findByid(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setStock(dto.getStock());
            product.setCategory(dto.getCategory());
            return Optional.of(productRepository.save(product));
        }
        return Optional.empty();
    }
}
