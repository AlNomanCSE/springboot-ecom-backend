package org.noman.ecommerce.service;
import org.noman.ecommerce.payload.CategoryDTO;
import org.noman.ecommerce.payload.CategoryResponse;
import org.springframework.stereotype.Repository;



public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
