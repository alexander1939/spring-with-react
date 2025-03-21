package com.softhugordc.taskmanagerappv1.security.service;

import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import com.softhugordc.taskmanagerappv1.dto.request.LoginRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.RegisterRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.UserResponseDTO;
import com.softhugordc.taskmanagerappv1.security.CustomUserDetails;
import com.softhugordc.taskmanagerappv1.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserService userService;
    private final AuthenticationManager authenticationManager;

    //Metodo para autenticar al usuario
    public UserResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity userEntity = customUserDetails.getUserEntity();
        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .build();
    }

    //Metodo para registrar al usuario
    public UserResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        UserEntity toSaveUserEntity;
        UserEntity savedUserEntity;
        toSaveUserEntity = UserEntity.builder()
                .name(registerRequestDTO.getName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .password(registerRequestDTO.getPassword())
                .build();
        savedUserEntity = userService.save(toSaveUserEntity);
        return UserResponseDTO.builder()
                .id(savedUserEntity.getId())
                .name(savedUserEntity.getName())
                .lastName(savedUserEntity.getLastName())
                .email(savedUserEntity.getEmail())
                .build();
    }

    //Metodo para obtener datos del usuario logueado
    public UserResponseDTO currentUser() throws AuthenticationCredentialsNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new AuthenticationCredentialsNotFoundException("No hay ningun usuario logueado");
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity userEntity = customUserDetails.getUserEntity();
        return UserResponseDTO.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .build();
    }

    //Metodo para cerrar sesion
    public void logout(){
        SecurityContextHolder.clearContext();
    }

}
