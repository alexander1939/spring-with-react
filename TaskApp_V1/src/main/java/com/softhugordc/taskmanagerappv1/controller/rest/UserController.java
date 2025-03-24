package com.softhugordc.taskmanagerappv1.controller.rest;

import com.softhugordc.taskmanagerappv1.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Controlador para usuarios
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    //Datos del usuario actual
    @GetMapping("/current-user")
    public ResponseEntity<?> currentUser() {
        return ResponseEntity.status(HttpStatus.OK).body(authService.currentUser());
    }

}
