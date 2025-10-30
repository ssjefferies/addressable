package com.seanjefferies.addressable.util;

import com.seanjefferies.addressable.model.Address;
import com.seanjefferies.addressable.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

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
        if (tokens.length < 2) {
            // see if the separator is a pipe
            tokens = line.split("\\|", -1);
            if (tokens.length < 2) {
                tokens = line.split("\\t", -1);
                if (tokens.length < 2) {
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
                    System.out.println("Unable to parse address from line: " + line);
                    e.printStackTrace();
                }
            }
        }

    }
}
