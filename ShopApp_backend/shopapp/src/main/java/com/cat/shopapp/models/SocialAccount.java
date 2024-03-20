package com.cat.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social_accounts")
@Data //toString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //do khóa chính này tăng lên 1 nên có thêm GenerationType.IDENTITY tức là k có bản ghi nào giống nhau
    private Long id;

    @Column(name = "provider", nullable = false, length = 20)
    private String provider;

    @Column(name = "provider_id", length = 50)
    private String providerId;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "email", length = 100)
    private String email;


}
