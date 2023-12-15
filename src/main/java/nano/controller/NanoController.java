package nano.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class NanoController {
    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("hi");
    }

    @RequestMapping("/api/bark")
    public ResponseEntity<?> bark(@RequestBody(required = false) String body) {
        log.info("bark body: %s".formatted(body));
        return ResponseEntity.ok(body);
    }
}
