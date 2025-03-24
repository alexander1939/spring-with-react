package com.softhugordc.taskmanagerappv1.controller.auth;

import com.softhugordc.taskmanagerappv1.dto.request.LoginRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.RegisterRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.MessageResponseDTO;
import com.softhugordc.taskmanagerappv1.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Controlador para autenticación
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //Inicio de sesion
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(loginRequestDTO, request, response));
    }

    //Registro de usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(registerRequestDTO));
    }

    //Cierre de sesion
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        authService.logout(request, response);
        MessageResponseDTO messageResponseDTO = MessageResponseDTO.builder()
                .message("Se ha cerrado la sesion")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(messageResponseDTO);
    }

}
