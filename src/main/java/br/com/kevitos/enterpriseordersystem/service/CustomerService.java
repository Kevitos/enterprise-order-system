package br.com.kevitos.enterpriseordersystem.service;

import org.springframework.stereotype.Service;

import br.com.kevitos.enterpriseordersystem.repository.CustomerRepository;
import java.util.Optional;

import br.com.kevitos.enterpriseordersystem.dto.CreateCustomerRequest;
import br.com.kevitos.enterpriseordersystem.entity.Customer;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CreateCustomerRequest request) {

        Optional<Customer> existingCustomer = customerRepository.findByEmailOrDocument(
                request.email(),
                request.document());

        if (existingCustomer.isPresent()) {
            throw new IllegalArgumentException("Customer already exists.");
        }

        Customer customer = new Customer(
                request.name(),
                request.email(),
                request.document());

        return customerRepository.save(customer);
    }

}