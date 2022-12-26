package com.postit.backend;

import com.postit.backend.exception.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableMongoRepositories
@Import(GlobalExceptionHandler.class)
@Slf4j
public class PostItApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostItApplication.class, args);
	}

}
