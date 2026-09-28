package spring_beginer.how_to_init_spring.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import spring_beginer.how_to_init_spring.models.UserDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        String username = userDTO.username;
        String password = userDTO.password;

        if ("admin".equals(username) && "123".equals(password)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test successful");
    }
}
