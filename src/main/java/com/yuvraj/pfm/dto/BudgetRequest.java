package com.yuvraj.pfm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetRequest {
    @NotBlank
    private String category;

    @NotNull
    private Double limitAmount;
}
