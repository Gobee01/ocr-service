package com.senz.ocr.service.configuration;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(60 * 60 * 1000); // 60 minutes
        factory.setReadTimeout(60 * 60 * 1000); // 60 minutes
        return new RestTemplate(factory);
    }

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        // Configure the server to not listen on IPv6
        factory.addConnectorCustomizers(connector -> {
            connector.setProperty("address", "0.0.0.0");
        });
        return factory;
    }
}

