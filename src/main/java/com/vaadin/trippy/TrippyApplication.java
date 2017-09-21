package com.vaadin.trippy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TrippyApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrippyApplication.class, args);
    }
}
