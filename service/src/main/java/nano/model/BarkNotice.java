package nano.model;

import lombok.Data;

import java.util.List;

@Data
public class BarkNotice {

    private BarkMessage message;
    private String recipient;
}
