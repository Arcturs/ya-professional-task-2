package ru.spb.itmo.asashina.yaprofessionaltask2.model.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class Group {

    @Id
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Long parentId;

}
