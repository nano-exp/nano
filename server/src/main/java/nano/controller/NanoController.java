package nano.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nano.service.NanoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/api/nano")
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
    return ResponseEntity.ok(this.nanoService.getProxyPAC_SU());
  }

  @ResponseBody
  @PostMapping("/proxy-su")
  public ResponseEntity<?> setProxySU(@RequestBody String value) {
    this.nanoService.assertAuthorized();
    this.nanoService.updateProxySU(value);
    return ResponseEntity.ok().build();
  }
}
