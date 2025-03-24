package com.softhugordc.taskmanagerappv1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO para representar los datos necesarios para crear una tarea
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskSaveRequestDTO {

    @NotBlank(message = "El campo id de usuario no debe estar vacio")
    private String idUser;

    @NotBlank(message = "El campo titulo no debe estar vacio")
    private String title;

    @NotBlank(message = "El campo descripcion no debe estar vacio")
    private String description;

}
