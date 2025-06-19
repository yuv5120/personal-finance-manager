package com.yuvraj.pfm.service;

import com.yuvraj.pfm.dto.CategoryRequest;
import com.yuvraj.pfm.dto.CategoryResponse;
import com.yuvraj.pfm.model.Category;
import com.yuvraj.pfm.model.User;
import com.yuvraj.pfm.repository.CategoryRepository;
import com.yuvraj.pfm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private User getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public CategoryResponse create(CategoryRequest request) {
        User user = getLoggedInUser();

        if (categoryRepository.existsByNameAndUser(request.getName(), user)) {
            throw new RuntimeException("Category already exists");
        }

        Category category = Category.builder()
                .name(request.getName())
                .user(user)
                .build();

        categoryRepository.save(category);
        return new CategoryResponse(category.getId(), category.getName());
    }

    public List<CategoryResponse> getAll() {
        User user = getLoggedInUser();
        return categoryRepository.findByUser(user)
                .stream()
                .map(cat -> new CategoryResponse(cat.getId(), cat.getName()))
                .collect(Collectors.toList());
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        User user = getLoggedInUser();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        category.setName(request.getName());
        categoryRepository.save(category);

        return new CategoryResponse(category.getId(), category.getName());
    }

    public void delete(Long id) {
        User user = getLoggedInUser();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        categoryRepository.delete(category);
    }
}
