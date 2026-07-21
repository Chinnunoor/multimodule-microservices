package com.example.microservices.common.util;

public final class TextFormatter {

    private TextFormatter() {
    }

    public static String orderMessage(String customerName, String orderId, String product, int quantity) {
        return "Hello %s, order %s was created for %d x %s."
                .formatted(customerName, orderId, quantity, product);
    }
}
