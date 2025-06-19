package com.yuvraj.pfm.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoalResponse {
    private Long id;
    private String title;
    private String description;
    private Double targetAmount;
    private LocalDate deadline;
    private Double progress; // % of balance saved
}
