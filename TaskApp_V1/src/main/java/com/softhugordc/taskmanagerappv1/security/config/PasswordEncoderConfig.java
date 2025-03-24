package com.softhugordc.taskmanagerappv1.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//Configuracion de encriptador
@Configuration
public class PasswordEncoderConfig {

    //Configura el encriptador de contraseñas a utilizar
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
