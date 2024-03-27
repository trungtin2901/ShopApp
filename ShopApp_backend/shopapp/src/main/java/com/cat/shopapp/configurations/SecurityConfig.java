package com.cat.shopapp.configurations;

import com.cat.shopapp.models.User;
import com.cat.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    //user's detail object
    private final UserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService(){
        return phoneNumber -> {
            User existingUser = userRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "Can not find user with phone number = "+ phoneNumber));
            return existingUser;
        };

        }
}
