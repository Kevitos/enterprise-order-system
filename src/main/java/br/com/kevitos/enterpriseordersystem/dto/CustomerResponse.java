package br.com.kevitos.enterpriseordersystem.dto;

import java.time.LocalDateTime;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        String document,
        LocalDateTime createdAt
) {
}