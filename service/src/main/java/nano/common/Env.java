package nano.common;

import static nano.common.ExceptionUtils.toValue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.repository.NanoRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Env {

  public enum Name {
    CDN_R2_HOST,
    R2_ACCESS_KEY,
    R2_SECRET_KEY,
    R2_ENDPOINT,
    TOKEN,
  }

  private final NanoRepository nanoRepository;

  public String getMetaEnv(Name name) {
    return toValue(() -> this.nanoRepository.getNanoMeta().get(name.name()), "Can't load meta data");
  }
}
