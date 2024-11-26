package org.noman.ecommerce.service;

import org.modelmapper.ModelMapper;
import org.noman.ecommerce.exceptions.APIException;
import org.noman.ecommerce.exceptions.ResourceNotFoundException;
import org.noman.ecommerce.model.Category;
import org.noman.ecommerce.payload.CategoryDTO;
import org.noman.ecommerce.payload.CategoryResponse;
import org.noman.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
//        Soring with pagination
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

//        only Adding pagination
//        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
        Page<Category> categories = categoryRepository.findAll(pageDetails);

        List<Category> allCategory = categories.getContent();

        if (allCategory.isEmpty()) throw new APIException("No Category Found till now");
        List<CategoryDTO> categoryDTO = allCategory.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTO);
        categoryResponse.setPageNumber(categories.getNumber());
        categoryResponse.setPageSize(categories.getSize());
        categoryResponse.setTotalElements(categories.getTotalElements());
        categoryResponse.setTotalPages(categories.getTotalPages());
        categoryResponse.setLastPage(categories.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (savedCategory != null)
            throw new APIException("Category with the name" + category.getCategoryName() + " already exists");
        Category save = categoryRepository.save(category);
        return modelMapper.map(save, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryID", categoryId));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        existingCategory = categoryRepository.save(category);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }

}
