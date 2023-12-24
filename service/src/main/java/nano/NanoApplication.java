package nano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanoApplication.class, args);
    }


}
