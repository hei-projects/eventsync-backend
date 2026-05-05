package com.example.eventsync_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class EventsyncBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsyncBackendApplication.class, args);
	}

}
