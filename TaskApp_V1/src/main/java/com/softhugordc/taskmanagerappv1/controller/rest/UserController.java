package com.softhugordc.taskmanagerappv1.controller.rest;

import com.softhugordc.taskmanagerappv1.service.IUserService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(@RequestParam(required = false) @NotBlank(message = "El campo id no debe estar vacio") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

}
