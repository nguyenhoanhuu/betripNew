package fit.iuh.dulichgiare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class DulichgiareApplication {

    public static void main(String[] args) {
        SpringApplication.run(DulichgiareApplication.class, args);
    }
}
