package nano.model;

import lombok.Data;
import nano.common.Env;
import nano.common.Json;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ObjectUtils;

import java.net.URI;


@Data
public class BarkNotice {

    private String content;
    private String recipient;
    private BarkMessage message;

    public static @NotNull String parseContent(@NotNull BarkMessage message) {
        var payload = message.getPayload();
        try {
            var template = "⚠️Message: [%s]%s\n\n%s\n\n%s";
            var parsed = Json.parseMap(payload);
            var id = message.getId();
            var domain = message.getDomain();
            var domainToAppend = ObjectUtils.isEmpty(domain) ? "" : " for " + domain;
            var title = (String) parsed.getOrDefault("title", payload);
            var link = URI.create(Env.NANO_HOST).resolve("/s/bark/m-" + id);
            return template.formatted(id, domainToAppend, title, link);
        } catch (Exception ex) {
            return payload;
        }
    }
}
