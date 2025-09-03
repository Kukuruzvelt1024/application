package ru.kukuruzvelt.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kukuruzvelt.application.domain.MovieEntity;


@SpringBootApplication
public class Application {
	public static String sourceBase = "B:\\src\\database_substitute.txt";

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}




}