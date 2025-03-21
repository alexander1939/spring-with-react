package com.softhugordc.taskmanagerappv1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO para representar los datos necesarios para login
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDTO {

    @NotBlank(message = "El campo email no debe estar vacio")
    private String email;

    @NotBlank(message = "El campo contraseña no debe estar vacio")
    private String password;

}
