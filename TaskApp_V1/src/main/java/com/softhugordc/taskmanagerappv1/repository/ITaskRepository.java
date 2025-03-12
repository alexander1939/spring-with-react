package com.softhugordc.taskmanagerappv1.repository;

import com.softhugordc.taskmanagerappv1.domain.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<TaskEntity, String> {

    @Query("select t from TaskEntity t join t.users u where u.id = :idUser")
    List<TaskEntity> findByIdUser(@Param("idUser") String idUser);

}
