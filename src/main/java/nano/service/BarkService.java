package nano.service;

import fetch.Fetch;
import lombok.extern.slf4j.Slf4j;
import nano.common.MapStore;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.util.List;

@Slf4j
@Service
public class BarkService {

    private static final String defaultBarkTitle = "[Nano Bark]";
    private final List<String> barkNoticeUrl;

    public BarkService(MapStore mapStore) {
        this.barkNoticeUrl = mapStore.get("barkNoticeUrl");
    }

    public void onBark(@NotNull String payload) {
        log.info("bark body: %s".formatted(payload));
        this.batchTriggerNotice(defaultBarkTitle, payload);
    }

    public void batchTriggerNotice(@NotNull String title, @NotNull String content) {
        var encodedTitle = UriUtils.encodeQuery(title, "utf8");
        var encodedContent = UriUtils.encodeQuery(content, "utf8");
        for (var url : this.barkNoticeUrl) {
            Fetch.fetchString(url.formatted(encodedTitle, encodedContent));
        }
    }
}
