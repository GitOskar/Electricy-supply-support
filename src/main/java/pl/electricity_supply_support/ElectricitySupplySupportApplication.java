package pl.electricity_supply_support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class ElectricitySupplySupportApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectricitySupplySupportApplication.class, args);
    }

}
