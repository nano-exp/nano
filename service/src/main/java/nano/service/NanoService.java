package nano.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.model.NanoMeta;
import nano.repository.NanoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NanoService {

    private final NanoRepository nanoRepository;

    public List<NanoMeta> getMetaData() {
        return this.nanoRepository.getNanoMeta();
    }
}
