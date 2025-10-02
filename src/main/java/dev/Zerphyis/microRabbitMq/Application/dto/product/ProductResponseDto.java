package dev.Zerphyis.microRabbitMq.Application.dto.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class ProductResponseDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private  String category;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
