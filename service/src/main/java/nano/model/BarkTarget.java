package nano.model;

import lombok.Data;

@Data
public class BarkTarget {
    private Integer id;
    private String name;
    private String url;
    private Boolean enabled;
}
