package com.yuvraj.pfm.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private Long id;
    private Double amount;
    private LocalDate date;
    private String category;
    private String description;
    private String type;
}
