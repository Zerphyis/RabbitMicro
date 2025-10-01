package dev.Zerphyis.microRabbitMq.Application.services;

import dev.Zerphyis.microRabbitMq.Application.dto.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Application.dto.ProductResponseDto;
import dev.Zerphyis.microRabbitMq.Application.mapper.ProductMapper;
import dev.Zerphyis.microRabbitMq.Application.useCases.*;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final CriarProduct createProduct;
    private final GetProduct getProductById;
    private final ListarProduct getAllProducts;
    private final DeletarProduct deleteProduct;
    private final FilterGetProduct filterGetProduct;
    private final AtualizarProduct atualizarProduct;

    public ProductService(
            CriarProduct createProduct,
            GetProduct getProductById,
            ListarProduct getAllProducts,
            DeletarProduct deleteProduct,
            FilterGetProduct filterGetProduct,
            AtualizarProduct atualizarProduct
    ) {
        this.createProduct = createProduct;
        this.getProductById = getProductById;
        this.getAllProducts = getAllProducts;
        this.deleteProduct = deleteProduct;
        this.filterGetProduct = filterGetProduct;
        this.atualizarProduct = atualizarProduct;
    }

    public ProductResponseDto create(ProductRequestDto dto) {
        Product saved = createProduct.execute(dto);
        return ProductMapper.toResponse(saved);
    }

    public ProductResponseDto getById(UUID id) {
        return ProductMapper.toResponse(getProductById.getById(id));
    }

    public List<ProductResponseDto> getAll() {
        return getAllProducts.execute();
    }
    public void delete(UUID id) {
        deleteProduct.execute(id);
    }

    public Page<ProductResponseDto> getProducts(
            String name,
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Page<Product> products = filterGetProduct.getProducts(name, category, minPrice, maxPrice, page, size, sortBy, direction);
        List<ProductResponseDto> dtoList = products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, products.getPageable(), products.getTotalElements());
    }

    public Optional<ProductResponseDto> update(UUID id, ProductRequestDto dto) {
        return atualizarProduct.execute(id, dto).map(ProductMapper::toResponse);
    }

    public Optional<ProductResponseDto> update(UUID id, Product product) {
        ProductRequestDto dto = ProductMapper.toRequestDto(product);
        return atualizarProduct.execute(id, dto).map(ProductMapper::toResponse);
    }
}
