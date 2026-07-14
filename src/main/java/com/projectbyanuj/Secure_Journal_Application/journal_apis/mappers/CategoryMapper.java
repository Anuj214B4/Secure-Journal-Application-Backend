package com.projectbyanuj.Secure_Journal_Application.journal_apis.mappers;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.CategoryRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.CategoryResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.entity.Category;

public class CategoryMapper {

    public static Category mapToEntity(CategoryRequest request) {
        return Category.builder()
                .categoryName(request.getCategoryName())
                .build();
    }

    public static CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .build();
    }
}
