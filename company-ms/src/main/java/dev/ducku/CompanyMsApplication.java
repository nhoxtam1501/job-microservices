package dev.ducku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CompanyMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyMsApplication.class, args);
    }

}
