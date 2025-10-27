package dev.Zerphyis.microRabbitMq.Application.dto.email;

public record Email(
        String to,
        String subject,
        String body) {
}
