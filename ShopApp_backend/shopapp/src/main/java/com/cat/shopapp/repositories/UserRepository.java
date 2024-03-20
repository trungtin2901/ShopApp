package com.cat.shopapp.repositories;

import com.cat.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);//Kiểm tra xem số đt của user có tồn tại hay chưa

    Optional<User> findByPhoneNumber(String phoneNumber);
}
