package com.seanjefferies.addressable.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="files_parsed")
public class FilesParsed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private LocalDateTime dateParsed;
    private Long totalLinesParsed;
    private Long linesWithErrors;
    private Long linesDelimitedWithCommas;
    private Long linesDelimitedWithPipes;
    private Long limesDelimitedWithTabs;

    public FilesParsed() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getDateParsed() {
        return dateParsed;
    }

    public void setDateParsed(LocalDateTime dateParsed) {
        this.dateParsed = dateParsed;
    }

    public Long getTotalLinesParsed() {
        return totalLinesParsed;
    }

    public void setTotalLinesParsed(Long totalLinesParsed) {
        this.totalLinesParsed = totalLinesParsed;
    }

    public Long getLinesWithErrors() {
        return linesWithErrors;
    }

    public void setLinesWithErrors(Long linesWithErrors) {
        this.linesWithErrors = linesWithErrors;
    }

    public Long getLinesDelimitedWithCommas() {
        return linesDelimitedWithCommas;
    }

    public void setLinesDelimitedWithCommas(Long linesDelimitedWithCommas) {
        this.linesDelimitedWithCommas = linesDelimitedWithCommas;
    }

    public Long getLinesDelimitedWithPipes() {
        return linesDelimitedWithPipes;
    }

    public void setLinesDelimitedWithPipes(Long linesDelimitedWithPipes) {
        this.linesDelimitedWithPipes = linesDelimitedWithPipes;
    }

    public Long getLimesDelimitedWithTabs() {
        return limesDelimitedWithTabs;
    }

    public void setLimesDelimitedWithTabs(Long limesDelimitedWithTabs) {
        this.limesDelimitedWithTabs = limesDelimitedWithTabs;
    }

    @Override
    public String toString() {
        return "FilesParsed{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", dateParsed=" + dateParsed +
                ", totalLinesParsed=" + totalLinesParsed +
                ", linesWithErrors=" + linesWithErrors +
                ", linesDelimitedWithCommas=" + linesDelimitedWithCommas +
                ", linesDelimitedWithPipes=" + linesDelimitedWithPipes +
                ", limesDelimitedWithTabs=" + limesDelimitedWithTabs +
                '}';
    }
}
