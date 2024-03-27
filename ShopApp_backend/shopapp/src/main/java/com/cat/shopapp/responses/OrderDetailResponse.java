package com.cat.shopapp.responses;

import com.cat.shopapp.models.Order;
import com.cat.shopapp.models.OrderDetail;
import com.cat.shopapp.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailResponse {
    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private Long product;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private Float totalMoney;

    private String color;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .product(orderDetail.getProduct().getId())
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .color(orderDetail.getColor())
                .build();
    }
}
