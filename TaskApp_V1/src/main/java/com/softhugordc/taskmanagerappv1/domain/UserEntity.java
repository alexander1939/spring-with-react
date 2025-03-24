package com.softhugordc.taskmanagerappv1.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

}
