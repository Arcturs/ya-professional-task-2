package ru.spb.itmo.asashina.yaprofessionaltask2.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GroupResponse {

    private Long id;
    private List<GroupResponse> subGroups;
    private String name;

}
