package com.mindex.challenge;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * Bootstrap for initializing the compensation repository
 * @author Emily Jackson
 */
@Component
public class CompensationBootstrap {
    //the file to store the compensation database in
    private static final String COMPENSATION_STORE_LOCATION = "/static/compensation_database.json";

    @Autowired
    private CompensationRepository compensationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Initialize the compensation repository by reading in and adding all entries
     * from the compensation database file
     */
    @PostConstruct
    public void init() {
        InputStream inputStream = this.getClass().getResourceAsStream(COMPENSATION_STORE_LOCATION);

        Compensation[] compensations = null;

        try {
            compensations = objectMapper.readValue(inputStream, Compensation[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Compensation compensation : compensations) {
            compensationRepository.insert(compensation);
        }
    }
}
