package org.noman.ecommerce.service;

import org.noman.ecommerce.model.Category;
import org.noman.ecommerce.payload.CategoryDTO;
import org.noman.ecommerce.payload.CategoryResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
