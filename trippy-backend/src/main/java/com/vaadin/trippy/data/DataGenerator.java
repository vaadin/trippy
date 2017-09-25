package com.vaadin.trippy.data;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(TripRepository tripRepository) {
        return args -> {
            createTrips(tripRepository);
        };
    }

    private void createTrips(TripRepository tripRepository) {
        for (int i = 0; i < 1000; i++) {
            Trip trip = new Trip();
            trip.setDate(LocalDate.now().minusWeeks(i));
            trip.setLength(i / Math.PI);
            trip.setData("Trip " + i);
            tripRepository.save(trip);
        }
        tripRepository.flush();
    }
}
