package dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.jpa;

import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.UUID;


public interface ProductRepositoryJpa extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
}
