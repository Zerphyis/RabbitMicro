package dev.Zerphyis.microRabbitMq.UnitTests.products;
import dev.Zerphyis.microRabbitMq.Application.useCases.products.ListarProduct;
import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductResponseDto;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ListarProduct listarProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar lista de produtos")
    void deveRetornarListaProdutos() {
        Product p1 = Product.builder()
                .name("Produto 1")
                .description("Descrição 1")
                .price(new BigDecimal("10.0"))
                .stock(5)
                .category("Eletrônicos")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product p2 = Product.builder()
                .name("Produto 2")
                .description("Descrição 2")
                .price(new BigDecimal("20.0"))
                .stock(2)
                .category("Informática")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<ProductResponseDto> result = listarProduct.execute();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Produto 1", result.get(0).getName());
        assertEquals("Produto 2", result.get(1).getName());

        verify(productRepository, times(1)).findAll();
    }
}
