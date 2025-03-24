package com.softhugordc.taskmanagerappv1.security.model;

import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

//Detalles del usuario basado en UserEntity
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    //Extrae los permisos del usuario (por si se manejan permisos)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    //Extrase el UserEntity
    public UserEntity getUserEntity() {
        return userEntity;
    }

    //Extrae la contraseña
    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    //Extrae el username, que en este caso seria el email
    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    //Verifica si la cuenta no esta expirada
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Verifica si la cuenta no esta bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //Verifica si las credenciales no estan expiradas
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //Verifica si la cuenta esta habilitada
    @Override
    public boolean isEnabled() {
        return true;
    }
}
