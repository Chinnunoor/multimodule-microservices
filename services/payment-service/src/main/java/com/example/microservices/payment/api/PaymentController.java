package com.example.microservices.payment.api;


import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@RequestMapping("/payments")
public class PaymentController {


    @PostMapping
    public Map<String,String> createPayment(
            @RequestParam String orderId,
            @RequestParam double amount
    ){


        return Map.of(

                "orderId", orderId,

                "amount", String.valueOf(amount),

                "status", "PAYMENT_SUCCESS"

        );

    }


}