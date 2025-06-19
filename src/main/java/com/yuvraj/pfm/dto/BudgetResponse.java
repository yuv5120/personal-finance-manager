package com.yuvraj.pfm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BudgetResponse {
    private Long id;
    private String category;
    private Double limitAmount;
    private Double totalSpent;
    private boolean exceeded;
}
