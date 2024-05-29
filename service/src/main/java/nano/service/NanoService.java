package nano.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.common.CurrentRequest;
import nano.model.NanoMeta;
import nano.repository.NanoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NanoService {

    private final NanoRepository nanoRepository;

    public boolean isAuthorized() {
        var incomingToken = Objects.requireNonNull(CurrentRequest.getCurrentRequest()).getHeader("X-Token");
        var token = nanoRepository.getNanoMetaValue("Token");
        return Objects.equals(incomingToken, token);
    }
}
