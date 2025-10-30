package com.seanjefferies.addressable.util;

import com.seanjefferies.addressable.model.Address;
import com.seanjefferies.addressable.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * This class runs when the application launches, reads in the data file,
 * and loads addresses from that file into the SQLite database so that we can
 * run our reports.
 */
@Component
public class InitializeData implements CommandLineRunner {

    @Autowired
    private ResourceLoader resourceLoader;

    private final AddressRepository addressRepository;

    public InitializeData(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    private Address parseAddress(String line) {
        Address addressEntity = new Address();
        String[] tokens = line.split(",", -1);
        // assume that if there is only one token in the array
        // after splitting the string on commas,
        // that a comma is NOT the separator.
        if (tokens.length < 2) {
            // see if the separator is a pipe
            tokens = line.split("\\|", -1);
            if (tokens.length < 2) {
                // see if the separator is a tab
                tokens = line.split("\\t", -1);
                if (tokens.length < 2) {
                    // we couldn't figure out how to split this line into address tokens
                    System.err.println("Unable to parse address from line: " + line);
                }
            }
        }
        System.out.println("tokens: " + Arrays.toString(tokens));
        addressEntity.setStreet(tokens[0].trim());
        addressEntity.setCity(tokens[1].trim());
        addressEntity.setState(tokens.length > 2 ? tokens[2].trim() : "");
        addressEntity.setZip(tokens.length > 3 ? tokens[3].trim(): "");
        return addressEntity;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing Addressible application...");
        // find the data file, parse it into separate addresses
        // and save those addresses to the database.
        Resource resource = resourceLoader.getResource("classpath:data1.txt");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                try {
                    Address addressEntity = parseAddress(line);
                    System.out.println(addressEntity);
                    addressRepository.save(addressEntity);
                } catch (Exception e) {
                    // something went wrong while reading this line from the file
                    // log this error, and move on to the next line
                    System.out.println("Unable to parse address from line: " + line);
                    e.printStackTrace();
                }
            }
        }

    }
}
