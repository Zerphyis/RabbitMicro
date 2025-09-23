package dev.Zerphyis.microRabbitMq.Application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    @NotBlank(message = "O nome do produto é obrigatório.")
    private String name;

    @NotBlank(message = "O produto deve ter uma descrição.")
    private String description;

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço deve ser maior ou igual a 0.")
    private BigDecimal price;

    @NotNull(message = "O estoque é obrigatório.")
    @Min(value = 0, message = "O estoque deve ser maior ou igual a 0.")
    private Integer stock;


    @NotBlank(message = "A categoria é obrigatória.")
    private String category;
}
