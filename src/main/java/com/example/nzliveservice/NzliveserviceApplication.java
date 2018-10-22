package com.example.nzliveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;


@SpringBootApplication
@EnableAutoConfiguration
public class NzliveserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NzliveserviceApplication.class, args);
	}
}
