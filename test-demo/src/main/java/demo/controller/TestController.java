package demo.controller;

import demo.entity.TestBean;
import demo.config.AbcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestBean testBean;

    @Autowired
    AbcConfig abcConfig;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/1")
    public void test(String name) {
        testBean.setName(name);
        testBean.setPassword("password");
    }

    @GetMapping("/2")
    public void getXAddY() {
        System.out.println(abcConfig.getdAddF());
        System.out.println(abcConfig.getDdd());
        System.out.println(abcConfig.getFff());
    }

    @GetMapping("/3")
    public void testRestTemplate3() {
        String url = "http://localhost:8080/test/4";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }

    @GetMapping("/4")
    public String testRestTemplate4() {
        String result = "444444";
        return result;
    }

}
