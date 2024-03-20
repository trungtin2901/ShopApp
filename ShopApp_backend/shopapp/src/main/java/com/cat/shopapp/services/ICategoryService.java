package com.cat.shopapp.services;

import com.cat.shopapp.dtos.CategoryDTO;
import com.cat.shopapp.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category);

    Category getCategoryById(long id);

    List<Category> getAllCategory();

    Category updateCategory(long categoryId, CategoryDTO category);

    void deleteCategory(long id);

}
