package com.cat.shopapp.services;

import com.cat.shopapp.dtos.CategoryDTO;
import com.cat.shopapp.dtos.OrderDTO;
import com.cat.shopapp.exceptions.DataNotFoundException;
import com.cat.shopapp.models.Category;
import com.cat.shopapp.models.Order;
import com.cat.shopapp.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;
    OrderResponse getOrderById(long id);
    List<OrderResponse> getAllOrder();
    OrderResponse updateOrder(long orderId, OrderDTO orderDTO);
    void deleteOrdery(long id);
}
