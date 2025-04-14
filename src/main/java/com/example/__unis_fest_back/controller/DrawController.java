package com.example.__unis_fest_back.controller;

import com.example.__unis_fest_back.dto.DrawResponseDto;
import com.example.__unis_fest_back.dto.ResultDto;
import com.example.__unis_fest_back.dto.WinRequestDto;
import com.example.__unis_fest_back.global.ApiResponse;
import com.example.__unis_fest_back.service.DrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/draw")
@RequiredArgsConstructor
public class DrawController {
    private final DrawService drawService;

    @PostMapping("/enter")
    public ResponseEntity<ApiResponse<?>> enter() {
        DrawResponseDto response = drawService.enter();
        return ResponseEntity.ok(ApiResponse.created(response));
    }

    @PostMapping("/win")
    public ResponseEntity<ApiResponse<?>> win(@RequestBody WinRequestDto request){
        drawService.win(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/results")
    public ResponseEntity<ApiResponse<?>> results(){
        ArrayList<ResultDto> responses = drawService.results();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
