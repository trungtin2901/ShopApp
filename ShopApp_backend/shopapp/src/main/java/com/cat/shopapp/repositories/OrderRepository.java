package com.cat.shopapp.repositories;

import com.cat.shopapp.models.Order;
import com.cat.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    //Tìm các đơn hàng của một user nào đó
    List<Order> findByUserId(Long userId);
}
