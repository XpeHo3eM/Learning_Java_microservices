package ru.mail.npv90.orderServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"ru.mail.npv90.orderServer", "ru.mail.npv90.general"})
public class OrderServer {
	public static void main(String[] args) {
		SpringApplication.run(OrderServer.class, args);
	}
}
