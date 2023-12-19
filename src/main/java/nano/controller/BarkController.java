package nano.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.common.Json;
import nano.service.BarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BarkController {

    private final BarkService barkService;

    @RequestMapping(path = "/api/bark")
    public ResponseEntity<?> bark(@RequestParam(required = false) Map<String, String> search,
                                  @RequestBody(required = false) String payload) {
        var getMessage = (Supplier<String>) () -> {
            if (!ObjectUtils.isEmpty(payload)) {
                return payload;
            } else if (!ObjectUtils.isEmpty(search)) {
                return Json.stringify(search);
            } else {
                return "[Bark from %s]".formatted(getCurrentRequestAddr());
            }
        };
        var message = getMessage.get();
        this.barkService.onBarkMessage(message);
        return ResponseEntity.ok(message);
    }

    @RequestMapping(path = "/api/bark/ack/{id}")
    public ResponseEntity<?> barkAck(@PathVariable("id") String id) {
        var result = this.barkService.ackBarkMessage(id);
        return ResponseEntity.ok(result);
    }

    private static String getCurrentRequestAddr() {
        var r = getCurrentRequest();
        return r != null ? r.getRemoteAddr() : null;
    }

    private static HttpServletRequest getCurrentRequest() {
        if (RequestContextHolder.currentRequestAttributes() instanceof ServletRequestAttributes requestAttributes) {
            return requestAttributes.getRequest();
        }
        return null;
    }
}
