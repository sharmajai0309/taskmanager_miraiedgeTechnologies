package com.MiraiEdge.Taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCaching
@Slf4j
@EnableScheduling
public class TaskApplication {
	

	public static void main(String[] args) {
		
		log.info("At Main class");
		SpringApplication.run(TaskApplication.class, args);
	}

}
