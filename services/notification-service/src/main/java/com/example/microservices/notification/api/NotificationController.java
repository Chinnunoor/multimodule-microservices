package com.example.microservices.notification.api;

import com.example.microservices.common.model.OrderCreatedEvent;
import com.example.microservices.common.util.TextFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @PostMapping("/order-created")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, String> orderCreated(@RequestBody OrderCreatedEvent event) {
        String message = TextFormatter.orderMessage(
                event.customerName(),
                event.orderId(),
                event.product(),
                event.quantity()
        );

        System.out.println("[notification-service] " + message);

        return Map.of(
                "status", "accepted",
                "message", message
        );
    }
}
