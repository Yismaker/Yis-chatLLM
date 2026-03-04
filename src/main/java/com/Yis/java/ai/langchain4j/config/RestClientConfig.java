package com.Yis.java.ai.langchain4j.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class RestClientConfig {

    @Value("${es.host:127.0.0.1}")
    private String host;

    @Value("${es.port:9200}")
    private Integer port;


    @Bean
    public RestClient client() {
        return RestClient.builder(new HttpHost(host, port, "http")).build();
    }

}