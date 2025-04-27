package com.example.__unis_fest_back.controller;

import com.example.__unis_fest_back.dto.DrawInfoDto;
import com.example.__unis_fest_back.global.ApiResponse;
import com.example.__unis_fest_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping({"", "/"})
    public String getDrawInfo(Model model) {
        log.info("Accessing /admin page.");  // 페이지 접근 로그

        List<DrawInfoDto> responses = adminService.getDrawInfo();
        if (responses == null) {
            log.error("Admin service returned null data.");
        } else {
            log.info("Received {} draw info entries.", responses.size());  // 데이터 크기 로그
        }

        model.addAttribute("drawList", responses);

        // 모델에 데이터가 잘 추가됐는지 확인하는 디버그 로그
        log.debug("Draw list added to model: {}", responses);

        return "admin";  // templates/admin.html 을 렌더링
    }
}
