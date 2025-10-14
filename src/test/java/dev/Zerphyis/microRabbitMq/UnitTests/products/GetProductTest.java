package dev.Zerphyis.microRabbitMq.UnitTests.products;
import dev.Zerphyis.microRabbitMq.Application.useCases.products.GetProduct;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;
import dev.Zerphyis.microRabbitMq.Infra.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private GetProduct getProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar produto quando existir")
    void deveRetornarProduto() {
        UUID id = UUID.randomUUID();
        Product p = Product.builder().name("Produto Teste").build();
        when(repository.findByid(id)).thenReturn(Optional.of(p));

        Product result = getProduct.getById(id);

        assertNotNull(result);
        assertEquals("Produto Teste", result.getName());
        verify(repository, times(1)).findByid(id);
    }

    @Test
    @DisplayName("Deve lançar ProductNotFoundException quando produto não existir")
    void deveLancarProductNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findByid(id)).thenReturn(Optional.empty());

        ProductNotFoundException ex = assertThrows(ProductNotFoundException.class,
                () -> getProduct.getById(id));

        assertEquals("Produto com id " + id + " não encontrado", ex.getMessage());
        verify(repository, times(1)).findByid(id);
    }
}

