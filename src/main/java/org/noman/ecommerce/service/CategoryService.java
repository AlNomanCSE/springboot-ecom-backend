package org.noman.ecommerce.service;

import org.noman.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    String createCategory(Category category);
    String deleteCategory(Long categoryId);


    String updateCategory(Category category, Long categoryId);
}
