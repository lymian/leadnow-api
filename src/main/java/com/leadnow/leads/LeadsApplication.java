package com.leadnow.leads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeadsApplication.class, args);
		System.out.println("----------------------------------");
		System.out.println("|    API REST Leadnow RUNNING    |");
		System.out.println("----------------------------------");
		System.out.println("Port: 8080");
		System.out.println("Swagger UI: http://localhost:8080/swagger-ui/index.html");
	}

}
