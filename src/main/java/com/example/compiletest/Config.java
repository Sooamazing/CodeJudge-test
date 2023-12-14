package com.example.compiletest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class Config {

	@Bean
	public S3Client s3Client() {
		// AmazonS3
		return new S3Client() {
			@Override
			public String serviceName() {
				return null;
			}

			@Override
			public void close() {

			}
		};
	}

}

