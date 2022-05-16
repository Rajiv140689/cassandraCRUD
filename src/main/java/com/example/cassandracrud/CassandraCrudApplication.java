package com.example.cassandracrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.*"})
public class CassandraCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CassandraCrudApplication.class, args);
    }

}
