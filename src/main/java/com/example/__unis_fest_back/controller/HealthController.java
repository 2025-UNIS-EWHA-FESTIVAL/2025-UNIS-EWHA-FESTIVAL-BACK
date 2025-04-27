package com.example.__unis_fest_back.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;


@Slf4j
@RestController
public class HealthController {

    @GetMapping("/")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/test")
    public String index(HttpServletRequest request) {
        String proto = request.getHeader("X-Forwarded-Proto");
        log.info("🔵 X-Forwarded-Proto = " + proto);
        return "ok";
    }

}
