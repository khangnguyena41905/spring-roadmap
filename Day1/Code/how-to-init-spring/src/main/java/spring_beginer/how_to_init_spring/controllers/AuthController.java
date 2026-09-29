package spring_beginer.how_to_init_spring.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import spring_beginer.how_to_init_spring.models.UserDTO;
import spring_beginer.how_to_init_spring.services.JwtService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor 
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        String token = jwtService.generateToken((UserDetails) auth.getPrincipal());
        return Map.of("accessToken", token);
    }

    public record LoginRequest(String username, String password) {}

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test successful");
    }
}
