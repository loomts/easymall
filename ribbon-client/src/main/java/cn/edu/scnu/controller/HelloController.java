package cn.edu.scnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {
	@Autowired
	private RestTemplate restTemplate;	
	//接收当前系统的请求地址hello
	@RequestMapping("hello")
	public String  sayHello(String name){
		String respongestr=restTemplate.getForObject("http://service01/hello?name="+name, String.class);
		return "来自于Ribbon："+respongestr;
	}
}
