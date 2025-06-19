package com.yuvraj.pfm.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionRequest {

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    private LocalDate date;

    @NotBlank
    private String category;

    private String description;

    @NotNull
    private String type; // INCOME or EXPENSE
}
