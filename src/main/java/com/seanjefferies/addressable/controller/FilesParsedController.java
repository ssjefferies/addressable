package com.seanjefferies.addressable.controller;

import com.seanjefferies.addressable.dto.FilesParsedDto;
import com.seanjefferies.addressable.service.FilesParsedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Apis for reports that show what happened as address files were parsed
 */
@RestController
@RequestMapping("/file")
public class FilesParsedController {

    private FilesParsedService filesParsedService;

    @Autowired
    public FilesParsedController(FilesParsedService filesParsedService) {
        this.filesParsedService = filesParsedService;
    }

    @GetMapping("/list")
    public List<FilesParsedDto> findAll() {
        return filesParsedService.findAll();
    }
}
