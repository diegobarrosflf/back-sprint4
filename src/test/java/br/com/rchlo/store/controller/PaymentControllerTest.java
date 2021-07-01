package br.com.rchlo.store.controller;

import br.com.rchlo.store.domain.Card;
import br.com.rchlo.store.domain.Payment;
import br.com.rchlo.store.domain.PaymentStatus;
import br.com.rchlo.store.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreatePayment() throws Exception {
        URI uri = new URI("/payments");
        String json = "{ \"value\": 79.9, \"cardClientName\": \"ANDERSON SILVA\", \"cardNumber\": \"1234567890120987\", \"cardExpiration\": \"2022-04\", \"cardVerificationCode\": \"121\"}";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void dontShouldConfirmPaymentAlreadyCanceledORAlreadyConfirmed() throws Exception {
        URI uri = new URI("/payments/3");
        mockMvc.perform(MockMvcRequestBuilders.put(uri))
        .andExpect(MockMvcResultMatchers
                .status()
                .is(400));
    }

    @Test
    public void dontShouldCancelPaymentAlreadyCanceledORAlreadyConfirmed() throws Exception {
        URI uri = new URI("/payments/4");
        mockMvc.perform(MockMvcRequestBuilders.put(uri))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(400));
    }
}