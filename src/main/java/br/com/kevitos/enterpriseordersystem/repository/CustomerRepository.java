package br.com.kevitos.enterpriseordersystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.kevitos.enterpriseordersystem.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByDocument(String document);

    Optional<Customer> findByEmailOrDocument(String email, String document);
}