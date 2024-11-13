package com.example.printer_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PrinterApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrinterApiApplication.class, args);
    }
}
