package com.cat.shopapp.controllers;

import com.cat.shopapp.dtos.OrderDTO;
import com.cat.shopapp.models.Order;
import com.cat.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import java.util.stream.Collectors;
@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid OrderDTO orderDTO,
            BindingResult result
    ){
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(order);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId){
        try {
            List<Order> orders = orderService.getAllOrder(userId);
            return ResponseEntity.ok(orders);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId){
        try {
            Order existingOrder = orderService.getOrderById(orderId);
            return ResponseEntity.ok(existingOrder);//"Lấy chi tiết của 1 đơn hàng nào đó từ order id"
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    //Công việc của admin
    public ResponseEntity<?> updateOrders(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody OrderDTO orderDTO
    ){
        try {
            Order order = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(order);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") Long id){
        //xóa mềm -> Cập nhật trường active =false
        orderService.deleteOrdery(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
