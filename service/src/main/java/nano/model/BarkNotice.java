package nano.model;

import lombok.Data;
import nano.common.Env;
import nano.common.Json;
import org.jetbrains.annotations.NotNull;

import java.net.URI;


@Data
public class BarkNotice {

    private String content;
    private String recipient;
    private BarkMessage message;

    public static @NotNull String parseContent(@NotNull BarkMessage message) {
        var payload = message.getPayload();
        try {
            var template = """
                    ⚠️Message: [%s]
                    
                    %s
                    
                    %s
                    """;
            var parsed = Json.parseMap(payload);
            var id = message.getId();
            var s = (String) parsed.getOrDefault("title", payload);
            var link = URI.create(Env.NANO_HOST).resolve("/s/bark/m-%s".formatted(id));
            return template.formatted(id, s, link);
        } catch (Exception ex) {
            return payload;
        }
    }
}
