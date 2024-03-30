package com.cat.shopapp.controllers;

import com.cat.shopapp.dtos.UserDTO;
import com.cat.shopapp.dtos.UserLoginDTO;
import com.cat.shopapp.models.User;
import com.cat.shopapp.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
            ){
        try{
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password does not match");
            }
            User user = userService.createUser(userDTO);
            //return ResponseEntity.ok("Register Successfully");
            return ResponseEntity.ok(user);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        //Kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        //Trả về token trong response
    }
}
