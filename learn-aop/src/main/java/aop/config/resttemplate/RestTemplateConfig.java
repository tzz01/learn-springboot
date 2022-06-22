package aop.config.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(35000);
        factory.setReadTimeout(35000);
        factory.setReadTimeout(60000);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setErrorHandler(new RestRequestErrorHanlder());
        return restTemplate;
    }
}
