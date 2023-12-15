package nano.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.service.BarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NanoController {

    private final BarkService barkService;

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("hi");
    }

    @RequestMapping("/api/bark")
    public ResponseEntity<?> bark(@RequestBody(required = false) String payload) {
        if (ObjectUtils.isEmpty(payload)) {
            payload = getCurrentQueryString();
        }
        if (ObjectUtils.isEmpty(payload)) {
            payload = "Bark from %s".formatted(getCurrentRequestAddr());
        }
        this.barkService.onBark(payload);
        return ResponseEntity.ok(payload);
    }

    private static String getCurrentRequestAddr() {
        var r = getCurrentRequest();
        return r != null ? r.getRemoteAddr() : null;
    }

    private static String getCurrentQueryString() {
        var r = getCurrentRequest();
        return r != null ? r.getQueryString() : null;
    }

    private static HttpServletRequest getCurrentRequest() {
        if (RequestContextHolder.currentRequestAttributes() instanceof ServletRequestAttributes requestAttributes) {
            return requestAttributes.getRequest();
        }
        return null;
    }
}
