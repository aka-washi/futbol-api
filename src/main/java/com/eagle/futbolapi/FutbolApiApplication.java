package com.eagle.futbolapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class FutbolApiApplication {

  public static void main(String[] args) {
    log.info("Starting Futbol API Application...");
    SpringApplication.run(FutbolApiApplication.class, args);
    log.info("Futbol API Application started successfully");
  }

}
