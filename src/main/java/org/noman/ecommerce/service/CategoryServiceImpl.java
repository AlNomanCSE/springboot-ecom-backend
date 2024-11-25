package org.noman.ecommerce.service;

import org.noman.ecommerce.exceptions.APIException;
import org.noman.ecommerce.exceptions.ResourceNotFoundException;
import org.noman.ecommerce.model.Category;
import org.noman.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> allCategory = categoryRepository.findAll();
        if(allCategory.isEmpty())  throw new APIException("No Category Found till now");
        return allCategory;
    }

    @Override
    public String createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null) throw new APIException("Category with the name"+category.getCategoryName()+" already exists");
        categoryRepository.save(category);
        return "Category created \uD83E\uDD9C";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryID",categoryId));

        categoryRepository.delete(category);
        return "\uD83E\uDEB0 Category deleted Successfully " + category.getCategoryId();
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryID",categoryId));
        existingCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(existingCategory);
        return "Category updated 🦑";
    }

}
