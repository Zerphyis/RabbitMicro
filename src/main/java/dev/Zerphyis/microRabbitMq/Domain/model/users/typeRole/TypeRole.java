package dev.Zerphyis.microRabbitMq.Domain.model.users.typeRole;

import jakarta.persistence.Table;

@Table(name = "permissoes")
public enum TypeRole {
    ADMIN,
    CLIENT
}
