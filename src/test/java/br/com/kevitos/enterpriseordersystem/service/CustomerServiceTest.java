package br.com.kevitos.enterpriseordersystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.kevitos.enterpriseordersystem.dto.CreateCustomerRequest;
import br.com.kevitos.enterpriseordersystem.dto.CustomerResponse;
import br.com.kevitos.enterpriseordersystem.entity.Customer;
import br.com.kevitos.enterpriseordersystem.exception.CustomerAlreadyExistsException;
import br.com.kevitos.enterpriseordersystem.exception.CustomerNotFoundException;
import br.com.kevitos.enterpriseordersystem.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

        @Mock
        private CustomerRepository customerRepository;

        @InjectMocks
        private CustomerService customerService;

        @Test
        void shouldReturnCustomerWhenCustomerExists() {

                Customer customer = new Customer(
                                "João da Silva",
                                "joao@email.com",
                                "12345678900");

                when(customerRepository.findById(1L))
                                .thenReturn(Optional.of(customer));

                Customer result = customerService.getCustomerById(1L);

                assertEquals("João da Silva", result.getName());
                assertEquals("joao@email.com", result.getEmail());
                assertEquals("12345678900", result.getDocument());
        }

        @Test
        void shouldThrowExceptionWhenCustomerDoesNotExist() {

                when(customerRepository.findById(999L))
                                .thenReturn(Optional.empty());

                CustomerNotFoundException exception = assertThrows(
                                CustomerNotFoundException.class,
                                () -> customerService.getCustomerById(999L));

                assertEquals(
                                "Cliente não encontrado para o ID: 999",
                                exception.getMessage());
        }

        @Test
        void shouldCreateCustomerWhenDataIsValid() {

                CreateCustomerRequest request = new CreateCustomerRequest(
                                "João da Silva",
                                "joao@email.com",
                                "12345678900");

                when(customerRepository.findByEmailOrDocument(
                                request.email(),
                                request.document()))
                                .thenReturn(Optional.empty());

                Customer customer = new Customer(
                                request.name(),
                                request.email(),
                                request.document());

                when(customerRepository.save(org.mockito.ArgumentMatchers.any(Customer.class)))
                                .thenReturn(customer);

                CustomerResponse result = customerService.createCustomer(request);

                assertEquals("João da Silva", result.name());
                assertEquals("joao@email.com", result.email());
                assertEquals("12345678900", result.document());
        }

        @Test
        void shouldThrowExceptionWhenCustomerAlreadyExists() {

                CreateCustomerRequest request = new CreateCustomerRequest(
                                "João da Silva",
                                "joao@email.com",
                                "12345678900");

                Customer customer = new Customer(
                                request.name(),
                                request.email(),
                                request.document());
                when(customerRepository.findByEmailOrDocument(
                                request.email(),
                                request.document()))
                                .thenReturn(Optional.of(customer));

                assertThrows(
                                CustomerAlreadyExistsException.class,
                                () -> customerService.createCustomer(request));
        }
}