package vn.dattb.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.dattb.auth.dto.AuthRequest;
import vn.dattb.auth.dto.AuthResponse;
import vn.dattb.auth.service.AuthService;

@RestController
@RequestMapping("/test")
public class TestController {

    private final AuthService authService;

    public TestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> test(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @GetMapping("/ping")
    public String test() {
        return "Test from Gateway";
    }

    @GetMapping("/ping-to-core")
    public String testToCore() {
        return "Test from Gateway to Core Service";
    }

    @PostMapping("/push")
    public ResponseEntity<String> push() {
        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/secured")
    public String securedEndpoint() {
        return "This is a secured endpoint";
    }
}
