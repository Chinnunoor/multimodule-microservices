package com.example.microservices.order.client;

import com.example.microservices.common.model.CustomerSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CustomerClient {

    private final RestClient restClient;

    public CustomerClient(
            RestClient.Builder builder,
            @Value("${services.customer.base-url}") String customerBaseUrl
    ) {
        this.restClient = builder.baseUrl(customerBaseUrl).build();
    }

    public CustomerSummary getCustomer(String customerId) {
        return restClient.get()
                .uri("/customers/{id}", customerId)
                .retrieve()
                .body(CustomerSummary.class);
    }
}
