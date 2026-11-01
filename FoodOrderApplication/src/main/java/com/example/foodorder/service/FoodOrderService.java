
package com.example.foodorder.service;

import com.example.foodorder.model.FoodOrder;
import com.example.foodorder.exception.FoodOrderNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@Service
public class FoodOrderService {

    private final ArrayList<FoodOrder> orders = new ArrayList<>();

    private long nextId = 1L;

    // 1. Add Food Order
    public synchronized FoodOrder addOrder(FoodOrder request) {

        FoodOrder order = new FoodOrder(
                nextId++,
                request.getCustomerName().trim(),
                request.getFoodItem().trim(),
                request.getQuantity(),
                request.getPrice()
        );

        orders.add(order);

        return copyOrder(order);
    }

    // 2. Get All Orders
    public synchronized List<FoodOrder> getAllOrders() {

        return orders.stream()
                .map(this::copyOrder)
                .toList();
    }

    // 3. Get Order By ID
    public synchronized FoodOrder getOrderById(Long id) {

        return copyOrder(findOrder(id));
    }

    // 4. Update Complete Order (PUT)
    public synchronized FoodOrder updateOrder(
            Long id, FoodOrder request) {

        FoodOrder order = findOrder(id);

        order.setCustomerName(request.getCustomerName().trim());
        order.setFoodItem(request.getFoodItem().trim());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());

        return copyOrder(order);
    }

    // 5. Delete Order
    public synchronized String deleteOrder(Long id) {

        FoodOrder order = findOrder(id);

        orders.remove(order);

        return "Order deleted successfully with ID: " + id;
    }

    // 6. Partial Update (PATCH)
    public synchronized FoodOrder patchOrder(
            Long id, FoodOrder request) {

        FoodOrder order = findOrder(id);

        if (request.getCustomerName() != null) {

            if (request.getCustomerName().isBlank()) {
                throw new IllegalArgumentException(
                        "Customer name cannot be blank");
            }

            order.setCustomerName(
                    request.getCustomerName().trim());
        }

        if (request.getFoodItem() != null) {

            if (request.getFoodItem().isBlank()) {
                throw new IllegalArgumentException(
                        "Food item cannot be blank");
            }

            order.setFoodItem(
                    request.getFoodItem().trim());
        }

        if (request.getQuantity() != null) {

            if (request.getQuantity() < 1) {
                throw new IllegalArgumentException(
                        "Quantity must be at least 1");
            }

            order.setQuantity(request.getQuantity());
        }

        if (request.getPrice() != null) {

            if (request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Price must be greater than zero");
            }

            order.setPrice(request.getPrice());
        }

        return copyOrder(order);
    }

    // Find Order
    private FoodOrder findOrder(Long id) {

        return orders.stream()
                .filter(item -> item.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new FoodOrderNotFoundException(
                                "Order not found with ID: " + id
                        ));
    }

    // Copy Order
    private FoodOrder copyOrder(FoodOrder order) {

        return new FoodOrder(
                order.getOrderId(),
                order.getCustomerName(),
                order.getFoodItem(),
                order.getQuantity(),
                order.getPrice()
        );
    }
}
