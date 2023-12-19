package nano.common;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class BarkBase {

    @Data
    public static class BarkTarget {
        private String name;
        private String url;
        private Boolean enabled;
    }

    @Data
    public static class BarkMessage {

        private String id = UUID.randomUUID().toString();
        private Boolean acked = false;
        private Integer noticeCount = 0;
        private String payload;
        private String ackTime;
        private String createTime = Instant.now().toString();

        public void increaseNoticeCount() {
            this.noticeCount++;
        }

        public void ack() {
            this.ackTime = Instant.now().toString();
            this.acked = true;
        }
    }

    private String barkHost;
    private List<BarkTarget> barkTargets = new ArrayList<>();
    private List<BarkMessage> barkMessages = new ArrayList<>();
    private Integer maxNoticeCount;

    public static BarkMessage createMessage(String payload) {
        BarkMessage message = new BarkMessage();
        message.setPayload(payload);
        return message;
    }
}
