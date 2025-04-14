package com.example.__unis_fest_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WinRequestDto {
    private Integer orderNumber;
    private String collegeName;
    private String phoneNumber;
    private String reviewText;
}
