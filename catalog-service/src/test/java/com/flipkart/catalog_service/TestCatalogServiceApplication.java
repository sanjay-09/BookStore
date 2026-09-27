package com.flipkart.catalog_service;

import org.springframework.boot.SpringApplication;

import java.util.TimeZone;

public class TestCatalogServiceApplication {

	public static void main(String[] args) {

        System.out.println("This is springboot application");
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));

        SpringApplication.from(CatalogServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
