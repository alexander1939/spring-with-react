package com.softhugordc.taskmanagerappv1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO para representar los datos necesarios de registro
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDTO {

    @NotBlank(message = "El campo nombre no debe estar vacio")
    private String name;

    @NotBlank(message = "El campo apellido no debe estar vacio")
    private String lastName;

    @NotBlank(message = "El campo email no debe estar vacio")
    private String email;

    @NotBlank(message = "El campo constraseña no debe estar vacio")
    private String password;

}
