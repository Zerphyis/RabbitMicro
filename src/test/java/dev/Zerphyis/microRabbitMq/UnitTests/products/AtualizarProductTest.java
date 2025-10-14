package dev.Zerphyis.microRabbitMq.UnitTests.products;
import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Application.useCases.products.AtualizarProduct;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class AtualizarProductTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private AtualizarProduct atualizarProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve atualizar produto existente com sucesso")
    void deveAtualizarProdutoExistente() {
        UUID id = UUID.randomUUID();
        Product existingProduct = Product.builder()
                .name("Produto Antigo")
                .description("Descrição antiga")
                .price(new BigDecimal("50"))
                .stock(5)
                .category("Eletrônicos")
                .build();

        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Produto Novo");
        dto.setDescription("Descrição nova");
        dto.setPrice(new BigDecimal("100"));
        dto.setStock(10);
        dto.setCategory("Informática");

        when(productRepository.findByid(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Product> result = atualizarProduct.execute(id, dto);

        assertTrue(result.isPresent());
        Product updated = result.get();
        assertEquals(dto.getName(), updated.getName());
        assertEquals(dto.getDescription(), updated.getDescription());
        assertEquals(dto.getPrice(), updated.getPrice());
        assertEquals(dto.getStock(), updated.getStock());
        assertEquals(dto.getCategory(), updated.getCategory());

        verify(productRepository, times(1)).findByid(id);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    @DisplayName("Deve retornar Optional.empty() quando produto não existir")
    void deveRetornarOptionalVazioQuandoNaoExistir() {
        UUID id = UUID.randomUUID();
        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Produto Novo");
        dto.setDescription("Descrição nova");
        dto.setPrice(new BigDecimal("100"));
        dto.setStock(10);
        dto.setCategory("Informática");

        when(productRepository.findByid(id)).thenReturn(Optional.empty());

        Optional<Product> result = atualizarProduct.execute(id, dto);

        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findByid(id);
        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve propagar exceção quando salvar no repositório falhar")
    void devePropagarExcecaoAoSalvar() {
        UUID id = UUID.randomUUID();
        Product existingProduct = Product.builder()
                .name("Produto Antigo")
                .description("Descrição antiga")
                .price(new BigDecimal("50"))
                .stock(5)
                .category("Eletrônicos")
                .build();

        ProductRequestDto dto = new ProductRequestDto();
        dto.setName("Produto Novo");
        dto.setDescription("Descrição nova");
        dto.setPrice(new BigDecimal("100"));
        dto.setStock(10);
        dto.setCategory("Informática");

        when(productRepository.findByid(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> atualizarProduct.execute(id, dto));

        assertEquals("Erro ao salvar", ex.getMessage());
        verify(productRepository, times(1)).findByid(id);
        verify(productRepository, times(1)).save(existingProduct);
    }
}
