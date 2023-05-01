package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StarterEurekaClient01 {

	public static void main(String[] args) {
		SpringApplication.run(StarterEurekaClient01.class, args);

	}

}
