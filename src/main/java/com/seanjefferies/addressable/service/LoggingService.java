package com.seanjefferies.addressable.service;

import com.seanjefferies.addressable.dto.LogDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoggingService {

    public static final String DELIMITER_PIPE = "\\|";
    public static final String LOG_FILE = "addressable.log";
    public static final String LEVEL_ERROR = "ERROR";
    public static final String LEVEL_WARN = "WARN";

    // create a logger instance
    Logger logger = LoggerFactory.getLogger(LoggingService.class);

    private final ResourceLoader resourceLoader;

    @Autowired
    public LoggingService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<LogDto> findErrorLogs() throws IOException {
        List<LogDto> logDtos = new ArrayList<>();
        // parse the file logs/addressable.log
        // TODO: get log file name from configuration instead
       return parseLogFile(LOG_FILE, LEVEL_ERROR);
    }

    private List<LogDto> parseLogFile(String logFile, String level) throws IOException {
        logger.debug("Parsing file: " + logFile);
        List<LogDto> logDtos = new ArrayList<>();

        Resource resource = resourceLoader.getResource("file:logs/" + logFile);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(DELIMITER_PIPE, -1);

                String logLevel = tokens.length > 1 ? tokens[1] : "";
                if (level == null || level.equals(logLevel)) {
                    String loggerName = tokens.length > 2 ? tokens[2] : "";
                    String message = tokens.length > 3 ? tokens[3] : "";
                    LogDto logDto = new LogDto(tokens[0], logLevel, loggerName, message);
                    logDtos.add(logDto);
                }
            }
        }
        return logDtos;
    }
}
