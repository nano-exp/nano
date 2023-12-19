package nano.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CornService {

    private final BarkService barkService;

    @Scheduled(fixedDelay = 30 * 1000)
    public void scheduledSendBarkMessage() {
        this.barkService.sendNoticeMessageNeeded();
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void scheduledPersistBarkBase() {
        this.barkService.persistStore();
    }
}
