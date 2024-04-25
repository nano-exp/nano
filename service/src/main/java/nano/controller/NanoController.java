package nano.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.service.NanoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NanoController {

    private final NanoService nanoService;

    @GetMapping("/hello")
    @ResponseBody
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("hi");
    }

    @ResponseBody
    @GetMapping("/meta-data")
    public ResponseEntity<?> getMetaData() {
        var metaData = this.nanoService.getMetaData();
        return ResponseEntity.ok(metaData);
    }
}
