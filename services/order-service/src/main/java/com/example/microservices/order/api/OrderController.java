package com.example.microservices.order.api;

import com.example.microservices.common.model.CustomerSummary;
import com.example.microservices.common.model.OrderCreatedEvent;
import com.example.microservices.common.util.IdGenerator;
import com.example.microservices.order.client.CustomerClient;
import com.example.microservices.order.client.NotificationClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CustomerClient customerClient;
    private final NotificationClient notificationClient;

    public OrderController(
            CustomerClient customerClient,
            NotificationClient notificationClient
    ) {
        this.customerClient = customerClient;
        this.notificationClient = notificationClient;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCreatedEvent createOrder(
            @RequestParam String customerId,
            @RequestParam String product,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        CustomerSummary customer = customerClient.getCustomer(customerId);

        OrderCreatedEvent event = new OrderCreatedEvent(
                IdGenerator.next("order"),
                customer.id(),
                customer.name(),
                product,
                quantity
        );

        notificationClient.sendOrderCreated(event);

        return event;
    }
}
