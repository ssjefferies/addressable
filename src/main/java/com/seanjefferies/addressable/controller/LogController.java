package com.seanjefferies.addressable.controller;

import com.seanjefferies.addressable.dto.LogDto;
import com.seanjefferies.addressable.service.LoggingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/logs")
public class LogController {

    private final LoggingService loggingService;

    public LogController(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @GetMapping("/error")
    public ResponseEntity<List<LogDto>> getErrorLogs() {
        try {
            return ResponseEntity.ok(loggingService.findErrorLogs());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
