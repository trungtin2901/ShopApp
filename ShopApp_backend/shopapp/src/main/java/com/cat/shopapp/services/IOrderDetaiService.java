package com.cat.shopapp.services;

import com.cat.shopapp.dtos.OrderDetailDTO;
import com.cat.shopapp.exceptions.DataNotFoundException;
import com.cat.shopapp.models.OrderDetail;

import java.util.List;

public interface IOrderDetaiService {
    OrderDetail createOderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;
    void deleteOrderDetail(Long id);
    List<OrderDetail> getAllOrderDetails(Long orderId);
}
