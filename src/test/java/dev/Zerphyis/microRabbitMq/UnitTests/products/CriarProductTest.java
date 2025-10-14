package dev.Zerphyis.microRabbitMq.UnitTests.products;

import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Application.mapper.product.ProductMapper;
import dev.Zerphyis.microRabbitMq.Application.useCases.products.CriarProduct;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CriarProductTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CriarProduct criarProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve criar um produto com sucesso")
    void deveCriarProdutoComSucesso() {

        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Produto Teste");
        dto.setDescription("Descrição do produto");
        dto.setPrice(new BigDecimal("99.99"));
        dto.setStock(10);
        dto.setCategory("Eletrônicos");

        Product product = ProductMapper.toEntity(dto);
        Product savedProduct = Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = criarProduct.execute(dto);

        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getPrice(), result.getPrice());
        assertEquals(dto.getStock(), result.getStock());
        assertEquals(dto.getCategory(), result.getCategory());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o repositório falhar")
    void deveLancarExcecaoQuandoRepositorioFalhar() {
        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Produto Teste");
        dto.setDescription("Descrição do produto");
        dto.setPrice(new BigDecimal("99.99"));
        dto.setStock(10);
        dto.setCategory("Eletrônicos");

        when(productRepository.save(any(Product.class)))
                .thenThrow(new RuntimeException("Erro ao salvar"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> criarProduct.execute(dto));
        assertEquals("Erro ao salvar", ex.getMessage());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
