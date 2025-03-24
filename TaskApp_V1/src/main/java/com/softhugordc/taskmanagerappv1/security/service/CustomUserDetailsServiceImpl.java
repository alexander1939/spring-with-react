package com.softhugordc.taskmanagerappv1.security.service;

import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import com.softhugordc.taskmanagerappv1.security.model.CustomUserDetails;
import com.softhugordc.taskmanagerappv1.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Servicio para cargar un usuario desde la base de datos
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    //Extrae el usuario por su username (el email para este caso), devuelve un UserDetails propio de spring
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("El email %s no esta registrado", email)));
        return new CustomUserDetails(userEntity);
    }
}
