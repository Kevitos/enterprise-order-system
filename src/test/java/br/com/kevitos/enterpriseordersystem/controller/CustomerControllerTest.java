package br.com.kevitos.enterpriseordersystem.controller;

import br.com.kevitos.enterpriseordersystem.service.CustomerService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.com.kevitos.enterpriseordersystem.entity.Customer;
import br.com.kevitos.enterpriseordersystem.exception.CustomerNotFoundException;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockitoBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn404WhenCustomerDoesNotExist() throws Exception {

        doThrow(new CustomerNotFoundException("Cliente não encontrado para o ID: 999"))
                .when(customerService)
                .getCustomerById(999L);

        mockMvc.perform(get("/customers/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Cliente não encontrado para o ID: 999"));
    }

    @Test
    void shouldReturnCustomerWhenCustomerExists() throws Exception {

        Customer customer = new Customer(
                "João da Silva",
                "joao@email.com",
                "12345678900");

        when(customerService.getCustomerById(1L))
                .thenReturn(customer);

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João da Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.document").value("12345678900"));
    }
}