package dev.Zerphyis.microRabbitMq.UnitTests.products;

import dev.Zerphyis.microRabbitMq.Application.useCases.products.DeletarProduct;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeletarProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeletarProduct deletarProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve deletar produto com sucesso")
    void deveDeletarProdutoComSucesso() {
        UUID id = UUID.randomUUID();
        doNothing().when(productRepository).deleteId(id);

        deletarProduct.execute(id);

        verify(productRepository, times(1)).deleteId(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o repositório falhar")
    void deveLancarExcecaoQuandoRepositorioFalhar() {
        UUID id = UUID.randomUUID();
        doThrow(new RuntimeException("Erro ao deletar")).when(productRepository).deleteId(id);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> deletarProduct.execute(id));
        assertEquals("Erro ao deletar", exception.getMessage());
        verify(productRepository, times(1)).deleteId(id);
    }

    @Test
    @DisplayName("Deve lançar NullPointerException quando o ID for nulo")
    void deveLancarExcecaoQuandoIdForNulo() {
        assertThrows(NullPointerException.class, () -> deletarProduct.execute(null));
        verify(productRepository, never()).deleteId(any());
    }
}
