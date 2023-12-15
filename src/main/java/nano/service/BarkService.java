package nano.service;

import fetch.Fetch;
import lombok.extern.slf4j.Slf4j;
import nano.common.Json;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriUtils;

import java.util.List;

@Slf4j
@Service
public class BarkService {

    private final List<String> barkNoticeUrl = this.initBarkNoticeUrl();

    public void onBark(@NotNull String payload) {
        log.info("bark body: %s".formatted(payload));
        var title = UriUtils.encode("Nano Bark", "utf8");
        var content = UriUtils.encode(String.valueOf(payload), "utf8");
        for (var url : this.barkNoticeUrl) {
            Fetch.fetchString(url.formatted(title, content));
        }
    }

    private @NotNull List<String> initBarkNoticeUrl() {
        var barkNoticeUrl = System.getenv("barkNoticeUrl");
        if (!ObjectUtils.isEmpty(barkNoticeUrl)) {
            return Json.parseList(barkNoticeUrl);
        } else {
            return List.of();
        }
    }
}
