package com.yuvraj.pfm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SummaryResponse {
    private double totalIncome;
    private double totalExpense;
    private double balance;
}
