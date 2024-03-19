package com.cat.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    @JsonProperty("order_id")
    @Min(value = 1, message = "order's id must be >0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's id must be > 0")
    private Long productId;

    @Min(value = 0, message = "price must be > 0")
    private Float price;

    @JsonProperty("number_of_product")
    @Min(value = 1, message = "number of product must be >= 1")
    private Long numberOfProduct;

    @Min(value = 0, message = "total must be >= 0")
    @JsonProperty("total_money")
    private int totalMoney;

    private String color;
}
