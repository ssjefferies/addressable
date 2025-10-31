package com.seanjefferies.addressable.dto;

public class LogDto {
    String date;
    String level;
    String logger;
    String message;

    public LogDto(String date, String level, String logger, String message) {
        this.date = date;
        this.level = level;
        this.logger = logger;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LogDto{" +
                "date='" + date + '\'' +
                ", level='" + level + '\'' +
                ", logger='" + logger + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
