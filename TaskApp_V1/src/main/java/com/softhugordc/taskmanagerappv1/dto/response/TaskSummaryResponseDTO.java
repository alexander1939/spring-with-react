package com.softhugordc.taskmanagerappv1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskSummaryResponseDTO {

    private String id;
    private String title;
    private String description;

}
