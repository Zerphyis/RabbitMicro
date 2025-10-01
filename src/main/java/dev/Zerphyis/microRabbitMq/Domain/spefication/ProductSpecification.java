package dev.Zerphyis.microRabbitMq.Domain.spefication;

import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {
    public static Specification<Product> filterBy(String name, String category, BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (name != null && !name.isBlank()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (category != null && !category.isBlank()) {
                predicate = cb.and(predicate, cb.equal(root.get("category"), category));
            }
            if (minPrice != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }
            if (maxPrice != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }
            return predicate;
        };
    }
}
