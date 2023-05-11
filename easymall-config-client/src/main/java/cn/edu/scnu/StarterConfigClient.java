
package cn.edu.scnu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient

public class StarterConfigClient {
    public static void main(String[] args) {
        SpringApplication.run(StarterConfigClient.class, args);
    }

    @Value("${name}")
    private String name;

    @Value("${location}")
    private String location;

    @RequestMapping("/yml")
    public String getYml() {
        return "name: " + name + ", location: " + location;
    }
}
