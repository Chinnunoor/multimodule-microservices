package com.example.microservices.common.model;

public record OrderCreatedEvent(
        String orderId,
        String customerId,
        String customerName,
        String product,
        int quantity
) {
}
