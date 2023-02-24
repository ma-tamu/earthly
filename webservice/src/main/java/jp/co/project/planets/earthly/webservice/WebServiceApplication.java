package jp.co.project.planets.earthly.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "jp.co.project.planets.earthly")
public class WebServiceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(WebServiceApplication.class, args);
    }
}
