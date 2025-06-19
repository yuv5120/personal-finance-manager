package com.yuvraj.pfm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MonthlyStatsResponse {
    private String month;
    private double income;
    private double expense;
}
