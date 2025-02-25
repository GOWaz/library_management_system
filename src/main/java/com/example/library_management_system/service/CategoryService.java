package com.example.library_management_system.service;

import com.example.library_management_system.entity.Category;
import com.example.library_management_system.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getById(Long id) {
        if (categoryRepository.existsById(id)) {
            return categoryRepository.findById(id);
        } else {
            throw new RuntimeException("Category with id " + id + " not found");
        }
    }

    public Category create(String name) {
        if (categoryRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Category with name " + name + " already exists");
        }
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public Category update(Long id, String name) {
        return categoryRepository.findById(id).map(
                category -> {
                    if (category.getName().equals(name)) {
                        throw new RuntimeException("Category with name " + name + " already exists");
                    }
                    category.setName(name);
                    return categoryRepository.save(category);
                }
        ).orElseThrow(() -> new RuntimeException("Category with id " + id + " not found"));
    }

    public void delete(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Category with id " + id + " not found");
        }
    }

}
