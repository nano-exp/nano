package nano.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nano.common.Env;
import nano.model.Vv;
import nano.repository.VvRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VvService {

  private final Env env;
  private final VvRepository vvRepository;
  private final R2Service r2Service;

  public @NotNull List<@NotNull Vv> search(
    @NotNull final String keyword,
    @NotNull final Integer pageIndex,
    @NotNull final Integer pageSize
  ) {
    var l = this.vvRepository.findVvList(keyword, pageSize, (pageIndex - 1) * pageSize);
    l.forEach(this::withCdn);
    return l;
  }

  public @NotNull Integer count(@NotNull final String keyword) {
    return this.vvRepository.countVvList(keyword);
  }

  @SneakyThrows
  public void newVv(@NotNull String filename, @NotNull Resource resource, String contentType) {
    var name = filename.replaceFirst("[.][^.]+$", "");
    var exist = this.vvRepository.getVvByName(name);
    if (exist == null) {
      var objectKey = "vv/" + filename;
      var response = this.r2Service.putObject(resource, objectKey, contentType);
      var etag = response.etag();
      var vv = new Vv();
      vv.setName(name);
      vv.setUrl("/vv/" + filename);
      vv.setComment(etag);
      this.vvRepository.createVv(vv);
    }
  }

  @SneakyThrows
  public void deleteVv(@NotNull Integer id) {
    var vv = this.vvRepository.getVvById(id);
    if (vv != null) {
      this.vvRepository.deleteVv(id);
      this.r2Service.removeObject(vv.getUrl().substring(1));
    }
  }

  public @NotNull Vv random() {
    var vv = this.vvRepository.getRandomVv();
    return this.withCdn(vv);
  }

  private Vv withCdn(Vv vv) {
    vv.setUrl(this.env.CDN_R2_HOST + vv.getUrl().substring(1));
    return vv;
  }
}
