package com.app;

import gray.light.owner.client.OwnerServiceClient;
import gray.light.owner.definition.business.OwnerDetailsBo;
import gray.light.support.web.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactor.core.publisher.Mono;

@EnableReactiveFeignClients(clients = OwnerServiceClient.class)
@EnableFeignClients
@SpringBootApplication
@RestController
public class App {

    @Autowired
    private OwnerServiceClient client;

    @RequestMapping("/")
    public Mono<ResponseFormat> hello() {
        return client.getOwnerDetails(1L);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
