package com.softhugordc.taskmanagerappv1.controller.auth;

import com.softhugordc.taskmanagerappv1.dto.request.LoginRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.RegisterRequestDTO;
import com.softhugordc.taskmanagerappv1.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

//Api para autenticación
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //Inicio de sesion
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(loginRequestDTO));
    }

    //Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(registerRequestDTO));
    }

    //Cierre de sesion
    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseEntity.status(HttpStatus.OK).body("Se ha cerrado tu sesion");
    }

}
