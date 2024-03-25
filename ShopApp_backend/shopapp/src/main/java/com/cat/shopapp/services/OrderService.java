package com.cat.shopapp.services;

import com.cat.shopapp.dtos.OrderDTO;
import com.cat.shopapp.exceptions.DataNotFoundException;
import com.cat.shopapp.models.Order;
import com.cat.shopapp.models.OrderStatus;
import com.cat.shopapp.models.User;
import com.cat.shopapp.repositories.OrderRepository;
import com.cat.shopapp.repositories.UserRepository;
import com.cat.shopapp.responses.OrderResponse;
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
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
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
        modelMapper.typeMap(Order.class, OrderResponse.class);
        OrderResponse orderResponse = new OrderResponse();
        modelMapper.map(order, orderResponse);
        return orderResponse;
    }

    @Override
    public OrderResponse getOrderById(long id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return null;
    }

    @Override
    public OrderResponse updateOrder(long orderId, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrdery(long id) {

    }
}
