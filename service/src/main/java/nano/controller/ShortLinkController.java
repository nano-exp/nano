package nano.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ShortLinkController {

    @GetMapping("/s/bark/m-{id}")
    public ResponseEntity<?> barkMessage(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/bark/message?id=%s".formatted(id)))
                .build();
    }
}
