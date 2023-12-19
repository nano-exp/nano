package nano.service;

import fetch.Fetch;
import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nano.common.BarkBase;
import nano.common.Json;
import nano.common.MapStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    @SneakyThrows
    public void sendNoticeMessage(@NotNull BarkBase.BarkMessage message) {
        var encodedTitle = UriUtils.encodeQuery(defaultBarkTitle, "utf8");
        var encodedContent = UriUtils.encodeQuery(message.getPayload(), "utf8");
        var urlHost = this.barkBase.getBarkHost();
        var urlPath = "/api/bark/ack/%s".formatted(message.getId());
        var encodedUrl = UriUtils.encode(new URL(new URL(urlHost), urlPath).toString(), "utf8");
        message.increaseNoticeCount();
        for (var it : this.barkBase.getBarkTargets()) {
            if (it.getEnabled()) {
                Fetch.fetchString(it.getUrl().formatted(encodedTitle, encodedContent, encodedUrl));
            }
        }
    }

    public List<BarkBase.BarkMessage> ackBarkMessage(@NotNull String id) {
        var messages = new ArrayList<BarkBase.BarkMessage>();
        for (var it : this.barkBase.getBarkMessages()) {
            if (Objects.equals(it.getId(), id)) {
                it.ack();
                messages.add(it);
            }
        }
        return messages;
    }

    @PreDestroy
    public void persistStore() {
        this.mapStore.set("barkBase", this.barkBase);
        this.mapStore.persist();
    }
}
