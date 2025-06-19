package com.yuvraj.pfm.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GoalRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    @Positive
    private Double targetAmount;

    @NotNull
    private LocalDate deadline;
}
