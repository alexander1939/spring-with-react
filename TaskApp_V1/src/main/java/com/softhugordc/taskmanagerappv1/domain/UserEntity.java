package com.softhugordc.taskmanagerappv1.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

//Endidad usuario
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)", unique = true, nullable = false)
    private String id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;


    @NotBlank(message = "El apellido no puede estar vacio")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "El email no tiene el formato correcto")
    @NotBlank(message = "El email no puede estar vacio")
    private String email;

    @NotBlank(message = "El password no puede estar vacio")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_users_tasks",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_task"))
    private Set<TaskEntity> tasks;

}
