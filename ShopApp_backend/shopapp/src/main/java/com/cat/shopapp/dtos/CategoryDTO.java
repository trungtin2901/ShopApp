package com.cat.shopapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data //chú thích này sẽ cung cấp các hàm getter, setter để thay đổi dữ liệu trường name và đọc dữ liệu trươờng name ra
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "Category's name can not be empty")
    private String name;

}
