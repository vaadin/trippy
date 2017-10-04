package com.vaadin.trippy.data;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataGenerator {

    private String[] cities = new String[] { "San Francisco", "Oakland",
            "San Jose", "Mountain View", "Palo Alto" };

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

            String start = getRandom(cities);
            trip.setStart(start);

            while (true) {
                String end = getRandom(cities);
                if (!end.equals(start)) {
                    trip.setEnd(end);
                    break;
                }
            }

            tripRepository.save(trip);
        }
        tripRepository.flush();
    }

    private String getRandom(String[] values) {
        return values[ThreadLocalRandom.current().nextInt(values.length)];
    }
}
