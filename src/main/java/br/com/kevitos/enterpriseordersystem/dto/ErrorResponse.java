package br.com.kevitos.enterpriseordersystem.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String message,
        LocalDateTime timestamp
) {
}