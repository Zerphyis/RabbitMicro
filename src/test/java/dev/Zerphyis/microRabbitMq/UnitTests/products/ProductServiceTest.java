package dev.Zerphyis.microRabbitMq.UnitTests.products;

import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductRequestDto;
import dev.Zerphyis.microRabbitMq.Application.dto.product.ProductResponseDto;
import dev.Zerphyis.microRabbitMq.Application.mapper.product.ProductMapper;
import dev.Zerphyis.microRabbitMq.Application.services.product.ProductService;
import dev.Zerphyis.microRabbitMq.Application.useCases.products.*;
import dev.Zerphyis.microRabbitMq.Domain.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock private CriarProduct criarProduct;
    @Mock private GetProduct getProductById;
    @Mock private ListarProduct listarProduct;
    @Mock private DeletarProduct deletarProduct;
    @Mock private FilterGetProduct filterGetProduct;
    @Mock private AtualizarProduct atualizarProduct;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductRequestDto requestDto;
    private ProductResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = Product.builder()
                .id(UUID.randomUUID())
                .name("Produto Teste")
                .description("Descrição teste")
                .price(new BigDecimal("99.90"))
                .stock(10)
                .category("Eletrônicos")
                .build();

        requestDto = new ProductRequestDto();
        requestDto.setName("Produto Teste");
        requestDto.setDescription("Descrição teste");
        requestDto.setPrice(new BigDecimal("99.90"));
        requestDto.setStock(10);
        requestDto.setCategory("Eletrônicos");

        responseDto = ProductMapper.toResponse(product);
    }

    @Test
    @DisplayName("Deve criar produto com sucesso")
    void deveCriarProduto() {
        when(criarProduct.execute(any(ProductRequestDto.class))).thenReturn(product);

        ProductResponseDto result = productService.create(requestDto);

        assertNotNull(result);
        assertEquals(requestDto.getName(), result.getName());
        verify(criarProduct, times(1)).execute(requestDto);
    }

    @Test
    @DisplayName("Deve retornar produto por ID")
    void deveRetornarProdutoPorId() {
        UUID id = product.getId();
        when(getProductById.getById(id)).thenReturn(product);

        ProductResponseDto result = productService.getById(id);

        assertNotNull(result);
        assertEquals(product.getName(), result.getName());
        verify(getProductById, times(1)).getById(id);
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosProdutos() {
        when(listarProduct.execute()).thenReturn(List.of(responseDto));

        List<ProductResponseDto> result = productService.getAll();

        assertEquals(1, result.size());
        verify(listarProduct, times(1)).execute();
    }

    @Test
    @DisplayName("Deve retornar página de produtos com filtros")
    void deveRetornarPaginaDeProdutos() {
        Page<Product> pageMock = new PageImpl<>(List.of(product), PageRequest.of(0, 5), 1);
        when(filterGetProduct.getProducts(any(), any(), any(), any(), anyInt(), anyInt(), any(), any()))
                .thenReturn(pageMock);

        Page<ProductResponseDto> result = productService.getProducts(
                "Teste", "Eletrônicos", BigDecimal.ZERO, BigDecimal.valueOf(1000),
                0, 5, "name", "ASC"
        );

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(product.getName(), result.getContent().get(0).getName());
        verify(filterGetProduct, times(1)).getProducts(any(), any(), any(), any(), anyInt(), anyInt(), any(), any());
    }

    @Test
    @DisplayName("Deve atualizar produto com sucesso")
    void deveAtualizarProduto() {
        UUID id = product.getId();
        when(atualizarProduct.execute(eq(id), any(ProductRequestDto.class)))
                .thenReturn(Optional.of(product));

        Optional<ProductResponseDto> result = productService.update(id, requestDto);

        assertTrue(result.isPresent());
        assertEquals(product.getName(), result.get().getName());
        verify(atualizarProduct, times(1)).execute(eq(id), any(ProductRequestDto.class));
    }

    @Test
    @DisplayName("Deve retornar Optional.empty() quando produto não for encontrado ao atualizar")
    void deveRetornarEmptyQuandoProdutoNaoEncontrado() {
        UUID id = product.getId();
        when(atualizarProduct.execute(eq(id), any(ProductRequestDto.class)))
                .thenReturn(Optional.empty());

        Optional<ProductResponseDto> result = productService.update(id, requestDto);

        assertTrue(result.isEmpty());
        verify(atualizarProduct, times(1)).execute(eq(id), any(ProductRequestDto.class));
    }

    @Test
    @DisplayName("Deve deletar produto por ID")
    void deveDeletarProduto() {
        UUID id = product.getId();

        productService.delete(id);

        verify(deletarProduct, times(1)).execute(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar produto se o caso de uso falhar")
    void deveLancarExcecaoAoCriarProduto() {
        when(criarProduct.execute(any(ProductRequestDto.class)))
                .thenThrow(new RuntimeException("Erro ao salvar"));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> productService.create(requestDto));

        assertEquals("Erro ao salvar", ex.getMessage());
        verify(criarProduct, times(1)).execute(requestDto);
    }
}
