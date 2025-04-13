package ru.spb.itmo.asashina.yaprofessionaltask2.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Student {

    @Id
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private Long groupId;

}
