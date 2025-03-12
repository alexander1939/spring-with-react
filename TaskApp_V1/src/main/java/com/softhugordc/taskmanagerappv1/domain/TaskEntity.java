package com.softhugordc.taskmanagerappv1.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

//Entidad tarea
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_tasks")
public class TaskEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)", unique = true, nullable = false)
    private String id;

    @NotBlank(message = "El titulo no puede estar vacio")
    private String title;

    @NotBlank(message = "La descripcion no puede estar vacia")
    private String description;

    @ManyToMany(mappedBy = "tasks")
    private Set<UserEntity> users;

}
