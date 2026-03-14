package com.example.course_management.service;

import com.example.course_management.dto.CategoryRequestDto;
import com.example.course_management.dto.CategoryResponseDto;
import com.example.course_management.entity.Category;
import com.example.course_management.exception.ResourceNotFoundException;
import com.example.course_management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllCategories(){

        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryResponseDto(
                        category.getId(),
                        category.getName(),
                        category.getCode(),
                        category.getDescription(),
                        category.isActive()
                ))
                .toList();
    }

    public CategoryResponseDto getCategoryById(Long id){

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getCode(),
                category.getDescription(),
                category.isActive()
        );
    }

    public CategoryResponseDto createCategory(CategoryRequestDto dto){

        Category category = new Category();

        category.setName(dto.getName());
        category.setCode(dto.getCode());
        category.setDescription(dto.getDescription());
        category.setActive(dto.isActive());

        categoryRepository.save(category);

        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getCode(),
                category.getDescription(),
                category.isActive()
        );
    }

    public void deleteCategory(Long id){

        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Category not found");
        }

        categoryRepository.deleteById(id);
    }
}