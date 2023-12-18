package nano;

import nano.common.MapStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanoApplication.class, args);
    }

    @Bean
    public MapStore mapStore() {
        var store = new MapStore("store.json");
        store.restore();
        return store;
    }
}
