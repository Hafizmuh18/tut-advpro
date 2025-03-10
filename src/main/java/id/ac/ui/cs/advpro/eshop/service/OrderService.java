package id.ac.ui.cs.advpro.eshop.service;

import id.ac.ui.cs.advpro.eshop.model.Order;

import java.util.List;

public interface OrderService {
    public Order createOrder(Order order);
    public Order updateStatus(String orderId, String status);
    public Order findById(String orderId);
    public List<Order> findAllByAuthor(String author);
}
