package br.com.kevitos.enterpriseordersystem.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.kevitos.enterpriseordersystem.dto.CreateCustomerRequest;
import br.com.kevitos.enterpriseordersystem.entity.Customer;
import br.com.kevitos.enterpriseordersystem.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer create(@RequestBody CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }
}
