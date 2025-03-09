package com.scrapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class URLScrappingApplication {

	public static void main(String[] args) {
		SpringApplication.run(URLScrappingApplication.class, args);
	}

}
