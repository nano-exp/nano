package nano.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.model.Vv;
import nano.repository.VvRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VvService {

    private final VvRepository vvRepository;

    public List<Vv> search(@NotNull final String keyword,
                           @NotNull final Integer pageIndex,
                           @NotNull final Integer pageSize) {
        return this.vvRepository.findVvList(keyword, pageSize, (pageIndex - 1) * pageSize);
    }
}
