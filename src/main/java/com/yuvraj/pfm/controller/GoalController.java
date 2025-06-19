package com.yuvraj.pfm.controller;

import com.yuvraj.pfm.dto.GoalRequest;
import com.yuvraj.pfm.dto.GoalResponse;
import com.yuvraj.pfm.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public GoalResponse create(@Valid @RequestBody GoalRequest request) {
        return goalService.create(request);
    }

    @GetMapping
    public List<GoalResponse> getAll() {
        return goalService.getAll();
    }

    @PutMapping("/{id}")
    public GoalResponse update(@PathVariable Long id, @RequestBody GoalRequest request) {
        return goalService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        goalService.delete(id);
        return "Goal deleted successfully";
    }
}
