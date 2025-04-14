package com.example.__unis_fest_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrawResponseDto {
    private Integer orderNumber;
    private Boolean isWinner;
    private String prize;
}
