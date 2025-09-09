package dev.Zerphyis.microRabbitMq.Domain.repository.jpa;

import dev.Zerphyis.microRabbitMq.Domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepositoryJpa extends JpaRepository<Product, UUID> {
}
