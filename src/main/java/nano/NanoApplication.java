package nano;

import nano.common.Json;
import nano.common.MapStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;

import java.util.List;

@SpringBootApplication
public class NanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanoApplication.class, args);
    }

    @Bean
    public MapStore mapStore() {
        var store = new MapStore("store.json");
        store.set("barkNoticeUrl", getBarkNoticeUrl());
        return store;
    }

    private static @NotNull List<String> getBarkNoticeUrl() {
        var barkNoticeUrl = System.getenv("barkNoticeUrl");
        if (!ObjectUtils.isEmpty(barkNoticeUrl)) {
            return Json.parseList(barkNoticeUrl);
        } else {
            return List.of();
        }
    }
}
