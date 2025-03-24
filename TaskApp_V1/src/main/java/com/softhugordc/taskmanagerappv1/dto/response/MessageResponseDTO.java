package com.softhugordc.taskmanagerappv1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO para representar un mensaje
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponseDTO {

    private String message;

}
