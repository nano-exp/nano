package nano.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.model.BarkMessage;
import nano.model.BarkNotice;
import nano.repository.BarkMessageRepository;
import nano.repository.NanoMetaRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BarkService {

    private final BarkMessageRepository barkMessageRepository;
    private final NanoMetaRepository nanoMetaRepository;

    @PostConstruct
    public void init() {
        this.nanoMetaRepository.assertNanoMetaTableExists();
    }

    public void onBarkCall(@NotNull String payload) {
        log.info("bark body: %s".formatted(payload));
        var message = new BarkMessage();
        message.setPayload(payload);
        message.setCreateTime(Instant.now().toString());
        var id = this.barkMessageRepository.create(message);
        if (id != null) {
            message.setId(id);
        }
    }

    public @NotNull List<BarkMessage> getNotAckMessageList() {
        return this.barkMessageRepository.getNotAckedList();
    }

    public @Nullable BarkMessage getMessage(@NotNull Integer id) {
        return this.barkMessageRepository.getById(id);
    }

    public @Nullable BarkMessage ackMessage(@NotNull Integer id, @NotNull String comment) {
        var message = this.barkMessageRepository.getById(id);
        if (message != null && message.getAckTime() == null) {
            var now = Instant.now().toString();
            this.barkMessageRepository.updateAckTime(id, now, comment);
            message.setAckTime(now);
        }
        return message;
    }

    public List<BarkNotice> getPendingNoticeList() {
        var messageList = this.getNotAckMessageList();
        var barkWxRoom = this.nanoMetaRepository.getValue("bark_wx_room");
        return messageList.stream().map((it) -> {
            var ni = new BarkNotice();
            ni.setMessage(it);
            ni.setRecipient(barkWxRoom);
            return ni;
        }).toList();
    }
}
