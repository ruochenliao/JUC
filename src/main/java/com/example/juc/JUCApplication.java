package com.example.juc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JUCApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(JUCApplication.class, args);
		}catch (Exception e){
			System.out.println(e.getMessage() + " " + e.getStackTrace());
		}
	}
}
