package com.softhugordc.taskmanagerappv1.repository;

import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    @Query("select u from UserEntity u join u.tasks t where t.id = :idTask")
    Optional<UserEntity> findByIdtask(@Param("idTask") String idTask);

}
