package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@SpringBootApplication
@EnableEurekaClient
public class StarterRibbonClient {
	public static void main(String[] args) {
		SpringApplication.run(StarterRibbonClient.class, args);
	}

	@Bean
	@LoadBalanced // 加了该注解，就开启负载均衡的功能
	public RestTemplate initRestTemplate() {
		return new RestTemplate();
	}

	// 创建一个自定义负载均衡策略
	@Bean
	public IRule myRule() {
		return new RandomRule();
	}

}
