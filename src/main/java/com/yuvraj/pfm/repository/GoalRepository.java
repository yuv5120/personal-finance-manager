package com.yuvraj.pfm.repository;

import com.yuvraj.pfm.model.Goal;
import com.yuvraj.pfm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUser(User user);
}
