package nano.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.common.Json;
import nano.service.BarkService;
import org.jetbrains.annotations.Nullable;
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
        this.barkService.onBark(message);
        return ResponseEntity.ok(message);
    }

    @PostMapping(path = "/api/bark/ack-message/{id}")
    public ResponseEntity<?> ackMessage(@PathVariable("id") Integer id) {
        var result = this.barkService.ackMessage(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/api/bark/message/{id}")
    public ResponseEntity<?> getMessage(@PathVariable("id") Integer id) {
        var message = this.barkService.getMessage(id);
        return ResponseEntity.ok(message);
    }

    private static String getCurrentRequestAddr() {
        var r = getCurrentRequest();
        if (r == null) {
            return null;
        }
        var ip = r.getHeader("X-Real-IP");
        if (ip == null) {
            ip = r.getRemoteAddr();
        }
        return ip;
    }

    private static @Nullable HttpServletRequest getCurrentRequest() {
        if (RequestContextHolder.currentRequestAttributes() instanceof ServletRequestAttributes requestAttributes) {
            return requestAttributes.getRequest();
        }
        return null;
    }
}
