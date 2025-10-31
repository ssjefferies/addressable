package com.seanjefferies.addressable.dto;

import java.time.LocalDateTime;

public class FilesParsedDto {
    private Long id;

    private String fileName;
    private LocalDateTime dateParsed;
    private Long totalLinesParsed;
    private Long linesWithErrors;
    private Long linesDelimitedWithCommas;
    private Long linesDelimitedWithPipes;
    private Long limesDelimitedWithTabs;

    public Long getId() {
        return id;
    }

    public FilesParsedDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public FilesParsedDto setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public LocalDateTime getDateParsed() {
        return dateParsed;
    }

    public FilesParsedDto setDateParsed(LocalDateTime dateParsed) {
        this.dateParsed = dateParsed;
        return this;
    }

    public Long getTotalLinesParsed() {
        return totalLinesParsed;
    }

    public FilesParsedDto setTotalLinesParsed(Long totalLinesParsed) {
        this.totalLinesParsed = totalLinesParsed;
        return this;
    }

    public Long getLinesWithErrors() {
        return linesWithErrors;
    }

    public FilesParsedDto setLinesWithErrors(Long linesWithErrors) {
        this.linesWithErrors = linesWithErrors;
        return this;
    }

    public Long getLinesDelimitedWithCommas() {
        return linesDelimitedWithCommas;
    }

    public FilesParsedDto setLinesDelimitedWithCommas(Long linesDelimitedWithCommas) {
        this.linesDelimitedWithCommas = linesDelimitedWithCommas;
        return this;
    }

    public Long getLinesDelimitedWithPipes() {
        return linesDelimitedWithPipes;
    }

    public FilesParsedDto setLinesDelimitedWithPipes(Long linesDelimitedWithPipes) {
        this.linesDelimitedWithPipes = linesDelimitedWithPipes;
        return this;
    }

    public Long getLimesDelimitedWithTabs() {
        return limesDelimitedWithTabs;
    }

    public FilesParsedDto setLimesDelimitedWithTabs(Long limesDelimitedWithTabs) {
        this.limesDelimitedWithTabs = limesDelimitedWithTabs;
        return this;
    }
}
