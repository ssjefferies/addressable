package com.seanjefferies.addressable.service;

import com.seanjefferies.addressable.dto.FilesParsedDto;
import com.seanjefferies.addressable.model.FilesParsed;
import com.seanjefferies.addressable.repository.FilesParsedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilesParsedService {

    private FilesParsedRepository filesParsedRepository;

    @Autowired
    public FilesParsedService(FilesParsedRepository filesParsedRepository) {
        this.filesParsedRepository = filesParsedRepository;
    }

    public List<FilesParsedDto> findAll() {
        return modelsToDtos(filesParsedRepository.findAll());
    }

    public static List<FilesParsedDto> modelsToDtos(List<FilesParsed> filesParsed) {
        return filesParsed.stream()
                .map(FilesParsedService::modelToDto)
                .collect(Collectors.toList());
    }

    public static FilesParsedDto modelToDto(FilesParsed filesParsed) {
        return new FilesParsedDto()
                .setId(filesParsed.getId())
                .setFileName(filesParsed.getFileName())
                .setDateParsed(filesParsed.getDateParsed())
                .setTotalLinesParsed(filesParsed.getTotalLinesParsed())
                .setLinesWithErrors(filesParsed.getLinesWithErrors())
                .setLinesDelimitedWithCommas(filesParsed.getLinesDelimitedWithCommas())
                .setLimesDelimitedWithTabs(filesParsed.getLimesDelimitedWithTabs())
                .setLinesDelimitedWithPipes(filesParsed.getLinesDelimitedWithPipes());
    }
}
