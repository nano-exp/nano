package nano.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.common.CurrentRequest;
import nano.common.Json;
import nano.service.BarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BarkController {

    private final BarkService barkService;

    @RequestMapping(path = "/api/bark/call")
    public ResponseEntity<?> bark(@RequestParam(required = false) Map<String, String> search,
                                  @RequestBody(required = false) String payload) {
        var getMessage = (Supplier<String>) () -> {
            if (!ObjectUtils.isEmpty(payload)) {
                return payload;
            } else if (!ObjectUtils.isEmpty(search)) {
                return Json.stringify(search);
            } else {
                return "[Bark from %s]".formatted(CurrentRequest.getCurrentRequestAddress());
            }
        };
        var message = getMessage.get();
        this.barkService.onBarkCall(message);
        return ResponseEntity.ok(message);
    }

    @PostMapping(path = "/api/bark/ack-message/{id}")
    public ResponseEntity<?> ackMessage(@PathVariable("id") Integer id,
                                        @RequestBody(required = false) String body) {
        var result = this.barkService.ackMessage(id, body != null ? body : "");
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/api/bark/message/{id}")
    public ResponseEntity<?> getMessage(@PathVariable("id") Integer id) {
        var message = this.barkService.getMessage(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping(path = "/api/bark/pending-notice")
    public ResponseEntity<?> getPendingNotice() {
        var noticeIntentionList = this.barkService.getPendingNoticeList();
        return ResponseEntity.ok(noticeIntentionList);
    }
}
