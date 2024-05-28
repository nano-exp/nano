package nano.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.model.Vv;
import nano.repository.VvRepository;
import org.jetbrains.annotations.NotNull;
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

    public @NotNull List<@NotNull Vv> search(@NotNull final String keyword,
                                             @NotNull final Integer pageIndex,
                                             @NotNull final Integer pageSize) {
        var l = this.vvRepository.findVvList(keyword, pageSize, (pageIndex - 1) * pageSize);
        if (ObjectUtils.isEmpty(keyword)) {
            l = new ArrayList<>(l);
            Collections.shuffle(l);
        }
        return l;
    }

    public @NotNull Integer count(@NotNull final String keyword) {
        return this.vvRepository.countVvList(keyword);
    }
}
