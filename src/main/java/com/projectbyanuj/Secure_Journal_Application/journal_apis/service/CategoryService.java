package com.projectbyanuj.Secure_Journal_Application.journal_apis.service;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.CategoryRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse addCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();

    boolean deleteCategory(Long id);
}
