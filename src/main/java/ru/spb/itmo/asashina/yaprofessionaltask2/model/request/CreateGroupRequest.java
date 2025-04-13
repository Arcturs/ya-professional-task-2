package ru.spb.itmo.asashina.yaprofessionaltask2.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupRequest {

    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    private Long parentId;

}
