package com.example.__unis_fest_back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrawInfoDto {
    private Integer orderNumber;
    private LocalDateTime entryTime;
    private String phoneNumber;
    private String college;
    private String prize;
}
