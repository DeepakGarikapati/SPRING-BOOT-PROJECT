
package com.example.foodorder.controller;

import com.example.foodorder.model.FoodOrder;
import com.example.foodorder.service.FoodOrderService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {

    private final FoodOrderService service;

    // Constructor Injection
    public FoodOrderController(FoodOrderService service) {
        this.service = service;
    }

    // 1. Add Food Order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodOrder addOrder(@Valid @RequestBody FoodOrder order) {
        return service.addOrder(order);
    }

    // 2. Get All Orders
    @GetMapping
    public List<FoodOrder> getAllOrders() {
        return service.getAllOrders();
    }

    // 3. Get Order By ID
    @GetMapping("/{id}")
    public FoodOrder getOrderById(@PathVariable Long id) {
        return service.getOrderById(id);
    }

    // 4. Update Complete Order
    @PutMapping("/{id}")
    public FoodOrder updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody FoodOrder order) {

        return service.updateOrder(id, order);
    }

    // 5. Delete Order
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        return service.deleteOrder(id);
    }

    // 6. Partial Update
    @PatchMapping("/{id}")
    public FoodOrder patchOrder(
            @PathVariable Long id,
            @RequestBody FoodOrder order) {

        return service.patchOrder(id, order);
    }
}
