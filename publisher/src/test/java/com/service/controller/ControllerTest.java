package com.service.controller;

import io.dapr.client.DaprClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

    @Mock
    private DaprClient daprClient;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    public void setup() {
        controller.setClient(daprClient); // Use the setter to inject the mock client.
    }

    @Test
    public void testPublishOrderSuccess() {
        Order order = new Order();
        order.setOrderId(1);

        when(daprClient.publishEvent(anyString(), anyString(), eq(order))).thenReturn(Mono.empty());

        Mono<ResponseEntity> responseMono = controller.publish(order);

        StepVerifier.create(responseMono)
                .expectNext(ResponseEntity.ok("SUCCESS"))
                .verifyComplete();
    }
}
