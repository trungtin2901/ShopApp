package com.cat.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //do khóa chính này tăng lên 1 nên có thêm GenerationType.IDENTITY tức là k có bản ghi nào giống nhau
    private Long id;

    @Column(name = "name", nullable = false, length = 350)
    private String name;


    private Float price;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    @Column(name = "description")
    private String description;



    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

}
