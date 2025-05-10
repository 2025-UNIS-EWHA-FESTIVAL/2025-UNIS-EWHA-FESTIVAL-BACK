package com.example.__unis_fest_back.controller;

import com.example.__unis_fest_back.global.ApiResponse;
import com.example.__unis_fest_back.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class BannerController {
    private static S3Service s3Service;

    @GetMapping("/api/banner")
    public ResponseEntity<ApiResponse<?>> getBannerImages() {
        ArrayList<String> responses = s3Service.getAllImageUrls();
        return (responses != null)?
                ResponseEntity.ok(ApiResponse.success(responses)):
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(500, "배너 이미지 불러오기에 실패했습니다."));
    }
}
