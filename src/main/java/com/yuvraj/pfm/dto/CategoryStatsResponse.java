package com.yuvraj.pfm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryStatsResponse {
    private String category;
    private double totalAmount;
}
