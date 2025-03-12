package com.softhugordc.taskmanagerappv1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDTO {

    private String id;
    private String title;
    private String description;
    private Set<UserSummaryResponseDTO> users;

}
