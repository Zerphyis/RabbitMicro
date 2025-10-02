package dev.Zerphyis.microRabbitMq.Infra.controllers;

import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductResponseDto;
import dev.Zerphyis.microRabbitMq.Application.services.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductRequestDto dto) {
        ProductResponseDto response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<ProductResponseDto>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Page<ProductResponseDto> dtoPage =
                service.getProducts(name, category, minPrice, maxPrice, page, size, sortBy, direction);

        return ResponseEntity.ok(dtoPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
