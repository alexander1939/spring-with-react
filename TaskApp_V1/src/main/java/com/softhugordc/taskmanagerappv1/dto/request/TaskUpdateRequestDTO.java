package com.softhugordc.taskmanagerappv1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO para representar los datos necesarios para actualizar una tarea
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskUpdateRequestDTO {

    @NotBlank(message = "El campo id de usuario no debe estar vacio")
    private String idUser;

    @NotBlank(message = "El campo id de tarea no debe estar vacio")
    private String id;

    @NotBlank(message = "El campo titulo no debe estar vacio")
    private String title;

    @NotBlank(message = "El campo descripcion no debe estar vacio")
    private String description;

}
