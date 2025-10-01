package dev.Zerphyis.microRabbitMq.Domain.repository.productRepository;

import dev.Zerphyis.microRabbitMq.Application.mapper.ProductMapper;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.jpa.ProductRepositoryJpa;
import dev.Zerphyis.microRabbitMq.Domain.spefication.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductRepositoryImpl implements ProductRepository{

    private final ProductRepositoryJpa jpaRepository;
    private final ProductMapper mapper;

    public ProductRepositoryImpl(ProductRepositoryJpa jpaRepository, ProductMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }


    @Override
    public Product save(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByid(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void deleteId(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Page<Product> findAllWithFilters(String name, String category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<Product> spec = ProductSpecification.filterBy(name, category, minPrice, maxPrice);
        return jpaRepository.findAll(spec, pageable);
    }
}
