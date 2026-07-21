package com.example.microservices.order.client;

import com.example.microservices.common.model.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NotificationClient {

    private final RestClient restClient;

    public NotificationClient(
            RestClient.Builder builder,
            @Value("${services.notification.base-url}") String notificationBaseUrl
    ) {
        this.restClient = builder.baseUrl(notificationBaseUrl).build();
    }

    public void sendOrderCreated(OrderCreatedEvent event) {
        restClient.post()
                .uri("/notifications/order-created")
                .body(event)
                .retrieve()
                .toBodilessEntity();
    }
}
