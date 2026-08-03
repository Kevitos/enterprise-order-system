package br.com.kevitos.enterpriseordersystem.dto;

public record CreateCustomerRequest(

        String name,
        String email,
        String document

) {
}