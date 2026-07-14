package com.projectbyanuj.Secure_Journal_Application.journal_apis.controller;

import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.requestes.CategoryRequest;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.dtos.responses.CategoryResponse;
import com.projectbyanuj.Secure_Journal_Application.journal_apis.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryRequest categoryRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.addCategory(categoryRequest));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getAllCategories());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        if(categoryService.deleteCategory(id)){
            return ResponseEntity.ok("Category deleted successfully.");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category not found.");
        }

    }
}
