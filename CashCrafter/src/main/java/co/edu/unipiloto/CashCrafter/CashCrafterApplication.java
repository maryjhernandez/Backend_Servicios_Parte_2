package co.edu.unipiloto.CashCrafter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "co.edu.unipiloto.CashCrafter")
@EntityScan("co.edu.unipiloto.CashCrafter.entity")
@EnableJpaRepositories("co.edu.unipiloto.CashCrafter.repository")
public class CashCrafterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CashCrafterApplication.class, args);
    }
}