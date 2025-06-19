package com.yuvraj.pfm.controller;

import com.yuvraj.pfm.dto.BudgetRequest;
import com.yuvraj.pfm.dto.BudgetResponse;
import com.yuvraj.pfm.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public BudgetResponse createOrUpdate(@Valid @RequestBody BudgetRequest request) {
        return budgetService.createOrUpdate(request);
    }

    @GetMapping
    public List<BudgetResponse> getAll() {
        return budgetService.getAll();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        budgetService.delete(id);
        return "Budget deleted successfully";
    }
}
