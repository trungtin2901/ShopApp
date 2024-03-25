package com.cat.shopapp.dtos;

import com.cat.shopapp.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's id must be > 0")
    private long productId;

    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "Image name must be between 5 and 200")
    private String imageUrl;
}
