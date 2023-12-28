package nano.model;

import lombok.Data;

@Data
public class BarkMessage {
    private Integer id;
    private String payload;
    private String ackTime;
    private String createTime;
    private String domain;
    private String comment;
}
