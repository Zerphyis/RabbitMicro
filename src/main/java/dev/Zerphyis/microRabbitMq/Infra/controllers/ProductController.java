package dev.Zerphyis.microRabbitMq.Infra.controllers;

import dev.Zerphyis.microRabbitMq.Application.dto.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Application.dto.ProductResponseDto;
import dev.Zerphyis.microRabbitMq.Application.mapper.ProductMapper;
import dev.Zerphyis.microRabbitMq.Application.services.ProductService;
import dev.Zerphyis.microRabbitMq.Domain.model.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductRequestDto dto) {
        Product product = ProductMapper.toEntity(dto);
        Product saved = service.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable UUID id) {
        Product product = service.getById(id);
        return ResponseEntity.ok(ProductMapper.toResponse(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll() {
        List<ProductResponseDto> products = service.getAll()
                .stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
