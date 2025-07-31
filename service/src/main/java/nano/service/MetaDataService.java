package nano.service;

import static nano.common.Ext.get;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.repository.NanoRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetaDataService {

  public enum Name {
    CDN_R2_HOST,
    R2_ACCESS_KEY,
    R2_SECRET_KEY,
    R2_ENDPOINT,
    TOKEN,
  }

  private final NanoRepository nanoRepository;

  public Map<Name, String> getMetaData() {
    var metaData = get(this.nanoRepository::getNanoMeta, "Can't load meta data");
    return Arrays.stream(Name.values()).collect(
      Collectors.toMap(Function.identity(), (name) -> metaData.get(name.name()))
    );
  }

  public String getMetaDataValue(Name name) {
    return this.getMetaData().get(name);
  }
}
