package com.yuvraj.pfm.controller;

import com.yuvraj.pfm.dto.LoginRequest;
import com.yuvraj.pfm.dto.RegisterRequest;
import com.yuvraj.pfm.dto.UserResponse;
import com.yuvraj.pfm.dto.GenericResponse;
import com.yuvraj.pfm.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
public GenericResponse login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
    authService.login(request, httpRequest);
    return new GenericResponse("Login successful");
}

    @PostMapping("/logout")
    public GenericResponse logout(HttpSession session) {
        session.invalidate();
        return new GenericResponse("Logout successful");
    }
}
