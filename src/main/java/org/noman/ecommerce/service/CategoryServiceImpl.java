package org.noman.ecommerce.service;

import org.noman.ecommerce.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    List<Category> categorise = new ArrayList<>();
    private Long nextId = 1l;

    @Override
    public List<Category> getAllCategories() {
        return categorise;
    }

    @Override
    public String createCategory(Category category) {
        category.setCategoryId(nextId++);
        categorise.add(category);
        return "Category created";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categorise.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

        categorise.remove(category);
        return "Category deleted Successfully " + category.getCategoryId();
    }

    @Override
    public String updateCategory(Category category, Long categoryId) {
        Optional<Category> foundCategory = categorise.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();
        if (foundCategory.isPresent()) {
            Category existingCategory = foundCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return "Category updated";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
    }

}
