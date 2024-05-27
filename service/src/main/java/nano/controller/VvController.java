package nano.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.model.Vv;
import nano.service.VvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vv")
public class VvController {

    private final VvService vvService;

    @GetMapping("/search")
    public ResponseEntity<List<Vv>> search(@RequestParam(name = "keyword", defaultValue = "") final String keyword,
                                           @RequestParam(name = "pageIndex", defaultValue = "1") final Integer pageIndex,
                                           @RequestParam(name = "pageSize", defaultValue = "10") final Integer pageSize) {
        var l = this.vvService.search(keyword, pageIndex, pageSize);
        return ResponseEntity.ok(l);
    }
}
