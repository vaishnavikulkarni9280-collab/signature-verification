package com.example.demo.auth.controllers;

import com.example.demo.auth.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ✅ Register with encrypted signature image upload
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("mobile") String mobile,
            @RequestParam(value = "department", required = false) String department,
            @RequestParam(value = "year", required = false) String year,
            @RequestParam("password") String password,
            @RequestParam("category") String category,
            @RequestParam(value = "signatureImage", required = false) MultipartFile signatureImage
    ) {
        String response = authService.registerUser(
                name, email, mobile, department, year, password, category, signatureImage
        );
        return ResponseEntity.ok(Map.of("message", response));
    }

    // ✅ Login (same as before)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Map<String, String> userData = authService.getUserDetails(
                request.get("email"),
                request.get("password")
        );

        if (userData != null) {
            return ResponseEntity.ok(userData);
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid Credentials"));
        }
    }
}
