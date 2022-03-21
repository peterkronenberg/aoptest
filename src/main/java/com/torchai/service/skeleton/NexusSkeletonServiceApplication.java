package com.torchai.service.skeleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.torchai.service"})
public class NexusSkeletonServiceApplication {

	public static void main(final String[] args) {
		SpringApplication.run(NexusSkeletonServiceApplication.class, args);
	}
}
