package dev.Zerphyis.microRabbitMq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MicroRabbitMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroRabbitMqApplication.class, args);
	}

}
