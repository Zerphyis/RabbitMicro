package dev.Zerphyis.microRabbitMq.Infra.config.configsProduct;

import dev.Zerphyis.microRabbitMq.Application.mapper.product.ProductMapper;
import dev.Zerphyis.microRabbitMq.Application.services.email.EmailProducerService;
import dev.Zerphyis.microRabbitMq.Application.services.product.ProductServiceRabbit;
import dev.Zerphyis.microRabbitMq.Application.useCases.products.*;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepository;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.ProductRepositoryImpl;
import dev.Zerphyis.microRabbitMq.Domain.repository.productRepository.jpa.ProductRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfig {

    @Bean
    public CriarProduct criarProduct(ProductRepository productRepository) {
        return new CriarProduct(productRepository);
    }

    @Bean
    public ListarProduct listarProduct(ProductRepository productRepository) {
        return new ListarProduct(productRepository);
    }

    @Bean
    public AtualizarProduct atualizarProduct(ProductRepository productRepository) {
        return new AtualizarProduct(productRepository);
    }

    @Bean
    public DeletarProduct deletarProduct(ProductRepository productRepository) {
        return new DeletarProduct(productRepository);
    }

    @Bean
    public GetProduct getProduct(ProductRepository productRepository) {
        return new GetProduct(productRepository);
    }

    @Bean
    public FilterGetProduct filterGetProduct(ProductRepository productRepository) {
        return new FilterGetProduct(productRepository);
    }

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapper();
    }

    @Bean
    public ProductRepository productRepository(ProductRepositoryJpa productRepositoryJpa,
                                               ProductMapper productMapper) {
        return new ProductRepositoryImpl(productRepositoryJpa, productMapper);
    }

    @Bean
    public EmailProducerService emailProducerService(ProductServiceRabbit productServiceRabbit){
        return  new EmailProducerService(productServiceRabbit);
    }
}
