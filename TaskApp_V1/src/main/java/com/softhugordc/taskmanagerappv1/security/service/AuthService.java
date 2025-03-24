package com.softhugordc.taskmanagerappv1.security.service;

import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import com.softhugordc.taskmanagerappv1.dto.request.LoginRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.RegisterRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.UserResponseDTO;
import com.softhugordc.taskmanagerappv1.security.model.CustomUserDetails;
import com.softhugordc.taskmanagerappv1.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

//Servicio para autenticacion
@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    //Autenticar al usuario
    public UserResponseDTO authenticate(LoginRequestDTO loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity userEntity = customUserDetails.getUserEntity();
        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .build();
    }

    //Registrar al usuario
    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        return userService.saveUser(registerRequestDTO);
    }

    //Obtener datos del usuario logueado
    public UserResponseDTO currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity userEntity = customUserDetails.getUserEntity();
        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .build();
    }

    //Cerrar sesion
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

}
