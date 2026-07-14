package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.CategoryRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.CategoryResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Category;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers.CategoryMapper;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        Category category = CategoryMapper.mapToEntity(categoryRequest);
        return CategoryMapper.mapToResponse(categoryRepository.save(category));
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (!categories.isEmpty())
            return categories.stream()
                    .map(CategoryMapper::mapToResponse)
                    .toList();
        else
            return List.of();
    }

    @Override
    public boolean deleteCategory(Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    return true;
                })
                .orElse(false);

    }
}
