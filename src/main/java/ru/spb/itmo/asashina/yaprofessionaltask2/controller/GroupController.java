package ru.spb.itmo.asashina.yaprofessionaltask2.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.request.CreateGroupRequest;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.request.UpdateGroupRequest;
import ru.spb.itmo.asashina.yaprofessionaltask2.service.GroupService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @PostMapping("")
    @Operation(
            summary = "Добавление группы с возможностью указания названия (name) " +
                    "и идентификатора группы родителя (parent_id)",
            tags = "Group Controller")
    public ResponseEntity<?> createGroup(@RequestBody @Valid CreateGroupRequest request) {
        return ResponseEntity.ok(groupService.createGroup(request));
    }

    @GetMapping("")
    @Operation(summary = "Получение списка всех групп", tags = "Group Controller")
    public ResponseEntity<?> getGroupsWithQuery(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(groupService.getAllGroups(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение группы по её идентификатору", tags = "Group Controller")
    public ResponseEntity<?> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.findGroup(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование группы по его идентификатору", tags = "Group Controller")
    public ResponseEntity<?> updateGroupById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateGroupRequest request) {

        return ResponseEntity.ok(groupService.updateGroup(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление группы по её идентификатору", tags = "Group Controller")
    public ResponseEntity<?> deleteGroupById(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

}
