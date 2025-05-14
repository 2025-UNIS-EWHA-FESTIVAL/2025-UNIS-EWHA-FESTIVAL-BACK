package com.example.__unis_fest_back.controller;

import com.example.__unis_fest_back.global.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
public class StatusController {
    @GetMapping("/api/status")
    public ResponseEntity<ApiResponse<?>> checkServerStatus() {
        LocalTime now = LocalTime.now();

        if (now.isAfter(LocalTime.of(22, 59)) || now.isBefore(LocalTime.of(10, 0))) {
            return ResponseEntity.ok(ApiResponse.success(false));
        }
        else return ResponseEntity.ok(ApiResponse.success(true));
    }
}
