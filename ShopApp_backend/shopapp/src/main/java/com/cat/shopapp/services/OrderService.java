package com.cat.shopapp.services;

import com.cat.shopapp.dtos.OrderDTO;
import com.cat.shopapp.exceptions.DataNotFoundException;
import com.cat.shopapp.models.Order;
import com.cat.shopapp.models.OrderStatus;
import com.cat.shopapp.models.User;
import com.cat.shopapp.repositories.OrderRepository;
import com.cat.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //Kiểm tra user'id có tồn tại hay không
//        Optional<User> optionalUser = userRepository.findById(orderDTO.getUserId());
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Can not find user with id: " + orderDTO.getUserId()));
        //conver orderDTO -> Order để insert vào db
        //Dùng thư viện ModelMapper
        //Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        //Cập nhật các trường của đơn hàng từ orderDTO
        Order order= new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        //Kiểm tra shipping date phải lớn hơn ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
                ? LocalDate.now() : orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today!");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        return order;
    }


    @Override
    public List<Order> getAllOrder(Long userId) {
        return orderRepository.findByUserId(userId);

    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order updateOrder(long orderId, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new DataNotFoundException("Can not find order with id " + orderId));
        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(() ->
                new DataNotFoundException("Can not find user with id " + orderId));
        // Tạo một luồng bảng anh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrdery(long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if(order != null){
            order.setActive(false);
            orderRepository.save(order);
        }
    }
}
