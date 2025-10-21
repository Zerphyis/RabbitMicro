package dev.Zerphyis.microRabbitMq.Domain.model.users.typeRole;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TypeRole {
    ADMIN,
    CLIENT;

    @JsonCreator
    public static TypeRole fromString(String value) {
        if (value == null) return null;
        return TypeRole.valueOf(value.toUpperCase());
    }
}
