package nano.service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nano.common.CurrentRequest;
import nano.common.Env;
import nano.repository.NanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class NanoService {

  private final Env env;
  private final NanoRepository nanoRepository;

  @Getter
  private String proxyPAC_SU;

  @SneakyThrows
  @Autowired
  public void setProxyPAC_SU(@Value("classpath:proxy.pac") Resource proxyPAC) {
    var proxy = this.nanoRepository.getNanoMeta().get("SU_LAN_PROXY");
    this.proxyPAC_SU = StreamUtils.copyToString(proxyPAC.getInputStream(), StandardCharsets.UTF_8).formatted(proxy);
  }

  public boolean isAuthorized() {
    var incomingToken = Objects.requireNonNull(CurrentRequest.getCurrentRequest()).getHeader("X-Token");
    return Objects.equals(incomingToken, this.env.TOKEN);
  }

  public void updateProxySU(String value) {
    this.nanoRepository.updateNanoMeta("SU_LAN_PROXY", value);
  }
}
