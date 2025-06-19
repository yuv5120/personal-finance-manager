package com.yuvraj.pfm.controller;

import com.yuvraj.pfm.dto.CategoryStatsResponse;
import com.yuvraj.pfm.dto.MonthlyStatsResponse;
import com.yuvraj.pfm.dto.SummaryResponse;
import com.yuvraj.pfm.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
    public SummaryResponse getSummary() {
        return analyticsService.getSummary();
    }

    @GetMapping("/monthly")
    public List<MonthlyStatsResponse> getMonthlyStats() {
        return analyticsService.getMonthlyStats();
    }

    @GetMapping("/category")
    public List<CategoryStatsResponse> getCategoryStats() {
        return analyticsService.getCategoryStats();
    }
}
