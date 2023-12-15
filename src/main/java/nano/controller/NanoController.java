package nano.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.service.BarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> bark(@RequestBody(required = false) String body) {
        this.barkService.onBark(body);
        return ResponseEntity.ok(body);
    }
}
