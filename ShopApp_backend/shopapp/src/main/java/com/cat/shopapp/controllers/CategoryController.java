package com.cat.shopapp.controllers;

import com.cat.shopapp.dtos.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import java.util.stream.Collectors;
@RestController
@RequestMapping("api/v1/categories")
//@Validated
public class CategoryController {
    //Hiển thị tất cả các categories
    @GetMapping("") //http://localhost:8088/api/v1/categories
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page")       int page,// @RequestParam("page") này được lấy từ client và chuyển vào tham số của phương thức ở đây là int page
            @RequestParam("limit")      int limit
    ){
        return ResponseEntity.ok(String.format("getAllCategories, page = %d, limt = %d", page, limit));
    }
    @PostMapping("")
    //Nếu tham số truyền vào là một đối tượng thì phải cho nó vào trong 1 class, được gọi là Data Transfer Object hay là Request Object
    public ResponseEntity<?> insertCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessage =result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        return ResponseEntity.ok("This is insert category" + categoryDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is update category with id = " + id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is delete category with id = " + id);
    }
}
