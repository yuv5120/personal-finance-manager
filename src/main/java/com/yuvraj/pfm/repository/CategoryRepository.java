package com.yuvraj.pfm.repository;

import com.yuvraj.pfm.model.Category;
import com.yuvraj.pfm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);
    boolean existsByNameAndUser(String name, User user);
}
