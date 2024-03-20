package com.cat.shopapp.controllers;

import com.cat.shopapp.dtos.CategoryDTO;
import com.cat.shopapp.models.Category;
import com.cat.shopapp.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import java.util.stream.Collectors;
@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
//@Validated
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("")
    //Nếu tham số truyền vào là một đối tượng thì phải cho nó vào trong 1 class, được gọi là Data Transfer Object hay là Request Object
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessage =result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok("insert category successfully");
    }
    //Hiển thị tất cả các categories
    @GetMapping("") //http://localhost:8088/api/v1/categories
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page")       int page,// @RequestParam("page") này được lấy từ client và chuyển vào tham số của phương thức ở đây là int page
            @RequestParam("limit")      int limit
    ){
        List<Category> newCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(newCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok("Update category successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("delete category with id = " + id + " successfully");
    }
}
