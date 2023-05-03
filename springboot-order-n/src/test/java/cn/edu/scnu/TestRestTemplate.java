package cn.edu.scnu;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class TestRestTemplate {
    @Test
    public void test01() {
        RestTemplate client = new RestTemplate();
        // 访问京东,get请求
        String bodyStr = client.getForObject("https://www.jd.com", String.class);
        System.out.println(bodyStr);
    }
}
