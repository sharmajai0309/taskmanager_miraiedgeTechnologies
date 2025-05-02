package com.MiraiEdge.Taskmanager.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MiraiEdge.Taskmanager.Services.AuthServices;
import com.MiraiEdge.Taskmanager.dto.AuthRequest;
import com.MiraiEdge.Taskmanager.dto.AuthResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


	@Autowired
	private final AuthServices authService;
	
	@PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
