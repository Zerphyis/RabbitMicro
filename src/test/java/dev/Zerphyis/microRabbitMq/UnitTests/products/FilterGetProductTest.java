package dev.Zerphyis.microRabbitMq.UnitTests.products;

import dev.Zerphyis.microRabbitMq.Application.useCases.products.FilterGetProduct;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilterGetProductTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private FilterGetProduct filterGetProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar página de produtos com filtros válidos")
    void deveRetornarPaginaDeProdutos() {
        Product p = Product.builder()
                .name("Produto 1")
                .description("Descrição Produto 1")
                .price(new BigDecimal("10"))
                .stock(5)
                .category("Eletrônicos")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Page<Product> pageMock = new PageImpl<>(Collections.singletonList(p));

        when(repository.findAllWithFilters(
                "Produto", "Categoria", BigDecimal.ZERO, BigDecimal.valueOf(1000),
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"))
        )).thenReturn(pageMock);

        Page<Product> result = filterGetProduct.getProducts(
                "Produto", "Categoria", BigDecimal.ZERO, BigDecimal.valueOf(1000),
                0, 10, "name", "ASC"
        );

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Produto 1", result.getContent().get(0).getName());
        verify(repository, times(1)).findAllWithFilters(
                any(), any(), any(), any(), any(PageRequest.class)
        );
    }

    @Test
    @DisplayName("Deve usar direção ASC quando o parâmetro direction for inválido")
    void deveDefaultarDirecaoASC() {
        Product p = Product.builder()
                .name("Produto 2")
                .description("Descrição Produto 2")
                .price(new BigDecimal("20"))
                .stock(3)
                .category("Informática")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Page<Product> pageMock = new PageImpl<>(Collections.singletonList(p));

        when(repository.findAllWithFilters(
                null, null, null, null,
                PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name"))
        )).thenReturn(pageMock);

        Page<Product> result = filterGetProduct.getProducts(
                null, null, null, null, 0, 5, null, "INVALID"
        );

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Produto 2", result.getContent().get(0).getName());
        verify(repository, times(1)).findAllWithFilters(
                any(), any(), any(), any(), any(PageRequest.class)
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o repositório falhar")
    void deveLancarExcecaoQuandoRepositorioFalhar() {
        when(repository.findAllWithFilters(
                any(), any(), any(), any(), any(PageRequest.class)
        )).thenThrow(new RuntimeException("Erro no banco"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                filterGetProduct.getProducts(null, null, null, null, 0, 5, null, null)
        );

        assertEquals("Erro no banco", exception.getMessage());
        verify(repository, times(1)).findAllWithFilters(
                any(), any(), any(), any(), any(PageRequest.class)
        );
    }
}
