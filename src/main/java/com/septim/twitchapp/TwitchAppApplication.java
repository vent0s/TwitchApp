package com.septim.twitchapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TwitchAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitchAppApplication.class, args);
	}

}
