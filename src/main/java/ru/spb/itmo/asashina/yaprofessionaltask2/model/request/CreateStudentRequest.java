package ru.spb.itmo.asashina.yaprofessionaltask2.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequest {

    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Email(message = "Неверно указана почта")
    @NotBlank(message = "Почта не может быть пустой")
    private String email;

    @NotNull(message = "Номер группы не может быть пустым")
    private Long groupId;

}
