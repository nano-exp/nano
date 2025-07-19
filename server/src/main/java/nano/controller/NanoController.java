package nano.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.service.NanoService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class NanoController {

  private final NanoService nanoService;

  @GetMapping("/hello")
  @ResponseBody
  public ResponseEntity<?> hello() {
    return ResponseEntity.ok("hi");
  }

  @ResponseBody
  @GetMapping(path = "/su.pac", produces = "application/x-ns-proxy-autoconfig.")
  public ResponseEntity<?> getProxyPAC_SU() {
    var body = this.nanoService.getProxyPAC_SU();
    return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(body);
  }

  @ResponseBody
  @PostMapping("/proxy-su")
  public ResponseEntity<?> setProxySU(@RequestBody String value) {
    this.nanoService.assertAuthorized();
    this.nanoService.updateProxySU(value);
    return ResponseEntity.ok().build();
  }
}
