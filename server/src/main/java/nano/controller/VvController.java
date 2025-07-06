package nano.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.model.Vv;
import nano.service.NanoService;
import nano.service.VvService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vv")
public class VvController {

    private final VvService vvService;
    private final NanoService nanoService;


    @GetMapping("/random")
    public ResponseEntity<Vv> random() {
        var vv = this.vvService.random();
        return ResponseEntity.ok(vv);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vv>> search(@RequestParam(name = "keyword", defaultValue = "") final String keyword,
                                           @RequestParam(name = "pageIndex", defaultValue = "1") final Integer pageIndex,
                                           @RequestParam(name = "pageSize", defaultValue = "10") final Integer pageSize) {
        var list = this.vvService.search(keyword, pageIndex, pageSize);
        var count = this.vvService.count(keyword);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(count))
                .body(list);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file,
                                    @RequestParam("filename") String filename) {
        if (this.nanoService.isAuthorized()) {
            this.vvService.newVv(filename, file.getResource(), file.getContentType());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") @NotNull Integer id) {
        if (this.nanoService.isAuthorized()) {
            this.vvService.deleteVv(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
