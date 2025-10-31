package com.seanjefferies.addressable.util;

import com.seanjefferies.addressable.model.Address;
import com.seanjefferies.addressable.model.FilesParsed;
import com.seanjefferies.addressable.repository.AddressRepository;

import com.seanjefferies.addressable.repository.FilesParsedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class runs when the application launches, reads in the data file,
 * and loads addresses from that file into the SQLite database so that we can
 * run our reports.
 */
@Component
public class InitializeData implements CommandLineRunner {

    public static final String STREET_PATTERN = "^(\\d*\\s*)(\\D+)(\\d.*)$";
    public static final String DELIMITER_COMMA = ",";
    public static final String DELIMITER_PIPE = "\\|";
    public static final String DELIMITER_TAB = "\\t";

    // create a logger instance
    Logger logger = LoggerFactory.getLogger(InitializeData.class);

    private final ResourceLoader resourceLoader;

    private final AddressRepository addressRepository;
    private final FilesParsedRepository filesParsedRepository;

    public InitializeData(AddressRepository addressRepository,
                          FilesParsedRepository filesParsedRepository,
                          ResourceLoader resourceLoader) {
        this.addressRepository = addressRepository;
        this.filesParsedRepository = filesParsedRepository;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Extract the street number, street name, and street unit
     * from a full street address
     * @param street
     * @param address
     */
    private void parseStreet(String street, Address address) {
        logger.debug("Parsing street " + street);
        Matcher matcher = Pattern.compile(STREET_PATTERN).matcher(street);
        if (matcher.find()) {
            String streetNumber = matcher.group(1);
            String streetName = matcher.group(2);
            String streetUnit = matcher.group(3);

            System.out.println("Street number " + streetNumber);
            System.out.println("Street name " + streetName);
            System.out.println("Street unit " + streetUnit);

            if (streetNumber != null && !streetNumber.trim().isEmpty()) {
                address.setStreetNumber(streetNumber.trim());
            }
            if (streetName != null && !streetName.trim().isEmpty()) {
                address.setStreetName(streetName.trim());
            }
            if (streetUnit != null && !streetUnit.trim().isEmpty()) {
                address.setStreetUnit(streetUnit.trim());
            }
        }
    }

    // TODO: put every separator into an array, and then loop over
    // that array until we find one that works.  This will make
    // it easier to add other separators to the process
    private ParseAddressResult parseAddress(String line) throws Exception {
        logger.debug("Parsing address line: {}", line);
        Address addressEntity = new Address();
        String delimeterUsed = DELIMITER_COMMA;
        String[] tokens = line.split(DELIMITER_COMMA, -1);
        // assume that if there is only one token in the array
        // after splitting the string on commas,
        // that a comma is NOT the separator.
        if (tokens.length < 2) {
            // see if the separator is a pipe
            tokens = line.split(DELIMITER_PIPE, -1);
            delimeterUsed =  DELIMITER_PIPE;
            if (tokens.length < 2) {
                // see if the separator is a tab
                tokens = line.split(DELIMITER_TAB, -1);
                delimeterUsed =  DELIMITER_TAB;
                if (tokens.length < 2) {
                    delimeterUsed = "";
                    // we couldn't figure out how to split this line into address tokens
                    System.err.println("Unable to parse address from line: " + line);
                    // TODO: create a more specific exception
                    throw new Exception("Unable to parse address from line: " + line);
                }
            }
        }
        String street = tokens[0].trim();
        parseStreet(street, addressEntity);

        addressEntity.setStreet(tokens[0].trim());
        addressEntity.setCity(tokens[1].trim());
        addressEntity.setState(tokens.length > 2 ? tokens[2].trim() : "");
        addressEntity.setZip(tokens.length > 3 ? tokens[3].trim(): "");
        return new ParseAddressResult()
                .setAddress(addressEntity)
                .setDelimiter(delimeterUsed);
    }

    private void saveReport(String fileName,
                            HashMap<String, Long> delimiters,
                            Long totalLinesParsed,
                            Long linesWithErrors) {
        FilesParsed filesParsed = new FilesParsed();
        filesParsed.setFileName(fileName);
        filesParsed.setDateParsed(LocalDateTime.now());

        filesParsed.setLinesDelimitedWithCommas(delimiters.get(DELIMITER_COMMA));
        filesParsed.setLimesDelimitedWithTabs(delimiters.get(DELIMITER_TAB));
        filesParsed.setLinesDelimitedWithPipes(delimiters.get(DELIMITER_PIPE));

        filesParsed.setTotalLinesParsed(totalLinesParsed);
        filesParsed.setLinesWithErrors(linesWithErrors);

        filesParsedRepository.save(filesParsed);
    }

    private void parseFile(String fileName) throws IOException {
        logger.debug("Parsing file: " + fileName);
        HashMap<String, Long> delimiters = new HashMap<>();
        long linesParsed = 0;
        long linesWithErrors = 0;

        // find the data file, parse it into separate addresses
        // and save those addresses to the database.
        Resource resource = resourceLoader.getResource("classpath:" + fileName);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                linesParsed++;
                System.out.println(line);
                try {
                    ParseAddressResult results = parseAddress(line);
                    System.out.println(results);
                    addressRepository.save(results.getAddress());
                    delimiters.put(results.getDelimiter(), delimiters.getOrDefault(results.getDelimiter(), 0L) + 1);
                } catch (Exception e) {
                    linesWithErrors++;
                    // something went wrong while reading this line from the file
                    // log this error, and move on to the next line
                    System.out.println("Unable to parse address from line: " + line);
                    e.printStackTrace();
                    logger.error("Unable to parse address from line: " + line);
                }
            }
            // finished parsing file
            // now save the report
            saveReport(fileName, delimiters, linesParsed, linesWithErrors);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing Addressible application...");
        logger.info("Initializing Addressible application...");
        String fileName = "data1.txt"; // TODO: get from an argument
        try {
            parseFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Unable to parse file: " + fileName);
        }
    }
}
