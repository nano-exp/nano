package nano.service;

import fetch.Fetch;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nano.model.BarkMessage;
import nano.model.NanoMeta;
import nano.repository.BarkMessageRepository;
import nano.repository.BarkTargetRepository;
import nano.repository.NanoMetaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.net.URL;
import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarkService {
    private static final String defaultBarkTitle = "[Nano Bark]";

    private final BarkMessageRepository barkMessageRepository;
    private final BarkTargetRepository barkTargetRepository;
    private final NanoMetaRepository nanoMetaRepository;

    private String barkHost;
    private Integer maxNoticeCount;

    @PostConstruct
    public void init() {
        var nanoMetaList = this.nanoMetaRepository.getAll();
        var nanoMeta = nanoMetaList.stream().collect(Collectors.toMap(NanoMeta::getName, NanoMeta::getValue));
        this.barkHost = nanoMeta.get("bark_host");
        this.maxNoticeCount = Integer.parseInt(nanoMeta.get("max_notice_count"));
    }

    public void onBarkMessage(@NotNull String payload) {
        log.info("bark body: %s".formatted(payload));
        var message = new BarkMessage();
        message.setPayload(payload);
        message.setCreateTime(Instant.now().toString());
        message.setNoticeCount(0);
        var id = this.barkMessageRepository.create(message);
        if (id != null) {
            message.setId(id);
            this.sendNoticeMessage(message);
        }
    }

    public void sendNoticeMessageNeeded() {
        var messageList = this.barkMessageRepository.getNotAckedList();
        for (var message : messageList) {
            if (message.getNoticeCount() <= this.maxNoticeCount) {
                this.sendNoticeMessage(message);
            }
        }
    }

    @SneakyThrows
    public void sendNoticeMessage(@NotNull BarkMessage message) {
        var encodedTitle = UriUtils.encodeQuery(defaultBarkTitle, "utf8");
        var encodedContent = UriUtils.encodeQuery(message.getPayload(), "utf8");
        var urlPath = "/api/bark/ack/%s".formatted(message.getId());
        var encodedUrl = UriUtils.encode(new URL(new URL(this.barkHost), urlPath).toString(), "utf8");
        this.barkMessageRepository.increaseNoticeCount(message.getId());
        var barkTargetList = this.barkTargetRepository.getAll();
        for (var it : barkTargetList) {
            if (it.getEnabled()) {
                Fetch.fetchString(it.getUrl().formatted(encodedTitle, encodedContent, encodedUrl));
            }
        }
    }

    public BarkMessage ackBarkMessage(@NotNull Integer id) {
        var message = this.barkMessageRepository.getById(id);
        if (message != null) {
            var now = Instant.now().toString();
            this.barkMessageRepository.ack(id, now);
            message.setAckTime(now);
        }
        return message;
    }
}
