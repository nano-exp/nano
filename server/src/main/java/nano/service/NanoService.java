package nano.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.common.CurrentRequest;
import nano.common.Env;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NanoService {

  private final Env env;

  public boolean isAuthorized() {
    var incomingToken = Objects.requireNonNull(CurrentRequest.getCurrentRequest()).getHeader("X-Token");
    return Objects.equals(incomingToken, this.env.TOKEN);
  }
}
