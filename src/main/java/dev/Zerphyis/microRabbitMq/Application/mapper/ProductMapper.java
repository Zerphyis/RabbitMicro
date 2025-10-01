package dev.Zerphyis.microRabbitMq.Application.mapper;

import dev.Zerphyis.microRabbitMq.Application.dto.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Application.dto.ProductResponseDto;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;

public class ProductMapper {
    public static Product toEntity(ProductRequestDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .category(dto.getCategory())
                .build();
    }

    public static ProductResponseDto toResponse(Product product) {
        if (product == null) return null;

        return ProductResponseDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .createdAt(product.getCreatedAt())
                .updateAt(product.getUpdatedAt())
                .build();
    }
    public static ProductRequestDto toRequestDto(Product product) {
        if (product == null) return null;

        ProductRequestDto dto = new ProductRequestDto();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategory(product.getCategory());
        return dto;
    }
}
