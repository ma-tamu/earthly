package jp.co.project.planets.earthly.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "jp.co.project.planets.earthly")
public class EarthlyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EarthlyApplication.class, args);
    }

}
