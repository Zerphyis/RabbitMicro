package dev.Zerphyis.microRabbitMq.Application.services.email;

import dev.Zerphyis.microRabbitMq.Application.dto.email.Email;
import dev.Zerphyis.microRabbitMq.Application.services.product.ProductServiceRabbit;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class EmailProducerService {
    private final ProductServiceRabbit serviceRabbit;

    public EmailProducerService(ProductServiceRabbit serviceRabbit) {
        this.serviceRabbit = serviceRabbit;
    }


    public void sendEmailToLoggedUser(String subject, String body) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Nenhum usuário autenticado encontrado.");
        }

        String userEmail = authentication.getName();

        Email email = new Email(userEmail, subject, body);

        serviceRabbit.sendMessage(email);

        System.out.println("📨 Evento de e-mail enviado para RabbitMQ com destino: " + userEmail);
    }
}
