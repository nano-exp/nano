package nano.service;

import fetch.Fetch;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import nano.common.BarkBase;
import nano.common.Json;
import nano.common.MapStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.util.Objects;

@Slf4j
@Service
public class BarkService {

    private static final String defaultBarkTitle = "[Nano Bark]";
    private final MapStore mapStore;
    private final BarkBase barkBase;

    public BarkService(MapStore mapStore) {
        this.mapStore = mapStore;
        var barkBaseFromStore = mapStore.get("barkBase");
        this.barkBase = Objects.requireNonNullElse(Json.convertValue(barkBaseFromStore, BarkBase.class), new BarkBase());
    }

    public void onBarkMessage(@NotNull String payload) {
        log.info("bark body: %s".formatted(payload));
        var message = BarkBase.createMessage(payload);
        this.barkBase.getBarkMessages().add(message);
        this.sendNoticeMessage(message);
    }

    public void sendNoticeMessageNeeded() {
        for (var message : this.barkBase.getBarkMessages()) {
            if (message.getAcked()) {
                continue;
            }
            if (message.getNoticeCount() <= this.barkBase.getMaxNoticeCount()) {
                this.sendNoticeMessage(message);
            }
        }
    }

    public void sendNoticeMessage(@NotNull BarkBase.BarkMessage message) {
        var encodedTitle = UriUtils.encodeQuery(defaultBarkTitle, "utf8");
        var encodedContent = UriUtils.encodeQuery(message.getPayload(), "utf8");
        message.increaseNoticeCount();
        for (var it : this.barkBase.getBarkTargets()) {
            if (it.getEnabled()) {
                Fetch.fetchString(it.getUrl().formatted(encodedTitle, encodedContent));
            }
        }
    }

    @PreDestroy
    public void onDestroy() {
        this.mapStore.set("barkBase", this.barkBase);
        this.mapStore.persist();
    }
}
