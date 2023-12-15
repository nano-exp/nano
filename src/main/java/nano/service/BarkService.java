package nano.service;

import fetch.Fetch;
import lombok.extern.slf4j.Slf4j;
import nano.common.Json;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class BarkService {

    private final List<String> barkNoticeUrl = initBarkNoticeUrl();

    public void onBark(String payload) {
        log.info("bark body: %s".formatted(payload));
        for (String url : this.barkNoticeUrl) {
            Fetch.fetchString(url.formatted("Nano Bark", payload));
        }
    }

    private List<String> initBarkNoticeUrl() {
        var barkNoticeUrl = System.getenv("barkNoticeUrl");
        if (!ObjectUtils.isEmpty(barkNoticeUrl)) {
            return Json.parseList(barkNoticeUrl);
        } else {
            return List.of();
        }
    }
}
