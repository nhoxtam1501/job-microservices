package dev.ducku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReviewMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewMsApplication.class, args);
    }

}
