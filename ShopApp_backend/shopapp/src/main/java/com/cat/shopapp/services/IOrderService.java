package com.cat.shopapp.services;

import com.cat.shopapp.dtos.OrderDTO;
import com.cat.shopapp.exceptions.DataNotFoundException;
import com.cat.shopapp.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    List<Order> getAllOrder(Long userId);

    Order getOrderById(Long id);


    Order updateOrder(long orderId, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrdery(long id);
}
