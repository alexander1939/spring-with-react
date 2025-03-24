package com.softhugordc.taskmanagerappv1.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softhugordc.taskmanagerappv1.dto.response.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//Configuracion personalizada del AuthenticationEntryPoint
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //Captura un error relacionado a autenticacion nula o vacia
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map<Class<? extends AuthenticationException>, Function<AuthenticationException, String>> errorMessages = generateErrorMessages();
        String errorMessage = errorMessages.getOrDefault(exception.getClass(), e -> "Inicia sesion para acceder a este recurso")
                .apply(exception);
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(errorMessage)
                .build();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponseDTO));
    }

    //Generar mensajes de error dependiendo a la excepcion
    private Map<Class<? extends AuthenticationException>, Function<AuthenticationException, String>> generateErrorMessages() {
        Map<Class<? extends AuthenticationException>, Function<AuthenticationException, String>> errorMessages = new HashMap<>();
        errorMessages.put(BadCredentialsException.class, e -> "Las credenciales son incorrectas");
        errorMessages.put(AuthenticationCredentialsNotFoundException.class, e -> "Las credenciales son necesarias");
        return errorMessages;
    }

}
