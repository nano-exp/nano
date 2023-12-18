package nano.common;

import lombok.Data;

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

        private String id;
        private String payload;
        private Integer noticeCount;
        private Boolean acked;

        public void increaseNoticeCount() {
            this.noticeCount++;
        }
    }

    private List<BarkTarget> barkTargets = new ArrayList<>();
    private List<BarkMessage> barkMessages = new ArrayList<>();
    private Integer maxNoticeCount;

    public static BarkMessage createMessage(String payload) {
        BarkMessage message = new BarkMessage();
        message.setId(UUID.randomUUID().toString());
        message.setNoticeCount(0);
        message.setPayload(payload);
        return message;
    }
}
