package nano;

import nano.common.MapStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanoApplication.class, args);
    }

    @Bean
    public MapStore mapStore() {
        return new MapStore("store.json");
    }
}
