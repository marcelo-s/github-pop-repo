package com.shopapotheke.githubpoprepo.configuration.web;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class WebConfiguration {

    @Value("${web-config.max_total_connections}")
    public int MAX_TOTAL_CONNECTIONS;
    @Value("${web-config.max_per_route_connections}")
    public int MAX_PER_ROUTE_CONNECTIONS = 1000;
    @Value("${web-config.connect_timeout}")
    public int CONNECT_TIMEOUT = 1000;
    @Value("${web-config.read_timeout}")
    public int READ_TIMEOUT = 5000;

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE_CONNECTIONS);

        return connectionManager;
    }

    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager connectionManager) {
        return HttpClientBuilder.create()
                                .setConnectionManager(connectionManager)
                                .build();
    }

    @Bean
    public RestTemplate restTemplate(CloseableHttpClient httpClient) {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(CONNECT_TIMEOUT))
                .setReadTimeout(Duration.ofMillis(READ_TIMEOUT))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }
}
