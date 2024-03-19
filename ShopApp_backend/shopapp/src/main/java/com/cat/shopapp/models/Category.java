package com.cat.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

//tầng model này dùng để ánh xạ từ các thực thể trong csdl sang các class của java spring
@Entity
@Table(name = "categories")
@Data //toString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //do khóa chính này tăng lên 1 nên có thêm GenerationType.IDENTITY tức là k có bản ghi nào giống nhau
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
}
