package com.softhugordc.taskmanagerappv1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO para representar los datos necesarios para actualizar parcialmente una tarea
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskPatchRequestDTO {

    @NotBlank(message = "El campo id de usuario no debe estar vacio")
    private String idUser;

    @NotBlank(message = "El campo id de tarea no debe estar vacio")
    private String id;

    private String title;

    private String description;

}
