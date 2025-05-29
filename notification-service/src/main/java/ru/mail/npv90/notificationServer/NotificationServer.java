package ru.mail.npv90.notificationServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.mail.npv90.notificationServer", "ru.mail.npv90.general"})
public class NotificationServer {
	public static void main(String[] args) {
		SpringApplication.run(NotificationServer.class, args);
	}
}
