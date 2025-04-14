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
public class ResultDto {
    private String collegeName;
    private String prize;
    private LocalDateTime entryTime;
    private String reviewText;
}
