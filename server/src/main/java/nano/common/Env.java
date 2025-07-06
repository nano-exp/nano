package nano.common;

import nano.repository.NanoRepository;
import org.springframework.stereotype.Component;

@Component
public class Env {

    public String CDN_R2_HOST;
    public String R2_ACCESS_KEY;
    public String R2_SECRET_KEY;
    public String R2_ENDPOINT;

    public Env(NanoRepository repository) {
        this.CDN_R2_HOST = repository.getNanoMetaValue("CDN_R2_HOST");
        this.R2_ACCESS_KEY = repository.getNanoMetaValue("R2_ACCESS_KEY");
        this.R2_SECRET_KEY = repository.getNanoMetaValue("R2_SECRET_KEY");
        this.R2_ENDPOINT = repository.getNanoMetaValue("R2_ENDPOINT");
    }
}
