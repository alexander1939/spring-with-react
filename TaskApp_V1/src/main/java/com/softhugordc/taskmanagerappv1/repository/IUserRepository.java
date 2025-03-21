package com.softhugordc.taskmanagerappv1.repository;

import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Repositorio para trabajar con UserEntity
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    String id(String id);
}
