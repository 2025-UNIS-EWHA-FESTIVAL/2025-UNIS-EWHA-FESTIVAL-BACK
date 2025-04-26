package com.example.__unis_fest_back.controller;

import com.example.__unis_fest_back.dto.DrawInfoDto;
import com.example.__unis_fest_back.global.ApiResponse;
import com.example.__unis_fest_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/")
    public String getDrawInfo(Model model) {
        List<DrawInfoDto> responses = adminService.getDrawInfo();
        model.addAttribute("drawList", responses); // 뷰에서 사용할 이름
        return "admin"; // templates/admin.html 을 렌더링
    }
}
