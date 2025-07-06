package nano.common;

import nano.repository.NanoRepository;
import org.springframework.stereotype.Component;

@Component
public class Env {

    public final String CDN_R2_HOST;
    public final String R2_ACCESS_KEY;
    public final String R2_SECRET_KEY;
    public final String R2_ENDPOINT;
    public final String TOKEN;

    public Env(NanoRepository repository) {
        var meta = repository.getNanoMeta();
        this.CDN_R2_HOST = meta.get("CDN_R2_HOST");
        this.R2_ACCESS_KEY = meta.get("R2_ACCESS_KEY");
        this.R2_SECRET_KEY = meta.get("R2_SECRET_KEY");
        this.R2_ENDPOINT = meta.get("R2_ENDPOINT");
        this.TOKEN = meta.get("TOKEN");
    }
}
