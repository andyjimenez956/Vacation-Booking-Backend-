package edu.wgu.dcn2.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "edu.wgu.dcn2")
public class Dcn2BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(Dcn2BackendApplication.class, args);
	}
}
