package com.example.microservices.customer.api;

import com.example.microservices.common.model.CustomerSummary;
import com.example.microservices.common.util.IdGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final Map<String, CustomerSummary> customers = new ConcurrentHashMap<>();

    public CustomerController() {
        customers.put(
                "c1",
                new CustomerSummary("c1", "Demo Customer", "demo@example.com")
        );
    }

    @GetMapping
    public Collection<CustomerSummary> all() {
        return customers.values();
    }

    @GetMapping("/{id}")
    public CustomerSummary findById(@PathVariable String id) {
        CustomerSummary customer = customers.get(id);

        if (customer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }

        return customer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerSummary create(
            @RequestParam String name,
            @RequestParam String email
    ) {
        String id = IdGenerator.next("customer");
        CustomerSummary customer = new CustomerSummary(id, name, email);
        customers.put(id, customer);
        return customer;
    }
}
