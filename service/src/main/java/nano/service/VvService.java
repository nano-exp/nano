package nano.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nano.common.Env;
import nano.model.Vv;
import nano.repository.NanoRepository;
import nano.repository.VvRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VvService {

    private final VvRepository vvRepository;
    private final NanoRepository nanoRepository;
    private final R2Service r2Service;

    public @NotNull List<@NotNull Vv> search(@NotNull final String keyword,
                                             @NotNull final Integer pageIndex,
                                             @NotNull final Integer pageSize) {
        var l = this.vvRepository.findVvList(keyword, pageSize, (pageIndex - 1) * pageSize);
        if (ObjectUtils.isEmpty(keyword)) {
            l = new ArrayList<>(l);
            Collections.shuffle(l);
        }
        for (var it : l) {
            var url = it.getUrl();
            it.setUrl(Env.CDN_R2_HOST + url.substring(1));
        }
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
            this.r2Service.putObject(resource, objectKey, contentType);
            var vv = new Vv();
            vv.setName(name);
            vv.setUrl("/vv/" + filename);
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
}
