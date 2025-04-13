package ru.spb.itmo.asashina.yaprofessionaltask2.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentRequest {

    @NotNull(message = "Поле ИД не может быть пустым")
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @NotNull(message = "Номер группы не может быть пустым")
    private Long groupId;

}
