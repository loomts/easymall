package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StarterUserCenter {
    public static void main(String[] args) {
        SpringApplication.run(StarterUserCenter.class, args);
    }
}
