package ru.spb.itmo.asashina.yaprofessionaltask2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.itmo.asashina.yaprofessionaltask2.exception.EntityAlreadyExistsException;
import ru.spb.itmo.asashina.yaprofessionaltask2.exception.EntityDoesNotExistException;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.entity.Group;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.request.CreateGroupRequest;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.request.UpdateGroupRequest;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.response.GroupResponse;
import ru.spb.itmo.asashina.yaprofessionaltask2.repository.GroupRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    @Transactional
    public Group createGroup(CreateGroupRequest request) {
        if (groupRepository.findByNameAndParentId(request.getName(), request.getParentId()).isPresent()) {
            throw new EntityAlreadyExistsException("Группа с данными именем и родительской группой уже существует");
        }
        if (groupRepository.findByParentId(request.getParentId()).isEmpty()) {
            throw new EntityDoesNotExistException("Родительская группа с данным ИД не существует");
        }
        return groupRepository.save(
                new Group()
                        .setName(request.getName())
                        .setParentId(request.getParentId()));
    }

    public List<GroupResponse> getAllGroups() {
        var groups = groupRepository.findAll();
        var map = new HashMap<Group, List<Group>>();
        for (var group : groups) {
            var key = map.getOrDefault(group, new ArrayList<>());
            key.add(group);
            map.put(group, key);
        }
        return map.entrySet()
                .stream()
                .map(it -> new GroupResponse()
                        .setId(it.getKey().getId())
                        .setSubGroups(
                                it.getValue()
                                        .stream()
                                        .map(el -> new GroupResponse()
                                                .setName(el.getName())
                                                .setId(el.getId()))
                                        .toList())
                        .setName(it.getKey().getName()))
                .toList();
    }

    public List<Group> getAllGroups(String query) {
        if (query == null || query.isEmpty()) {
            return groupRepository.findAll()
                    .stream()
                    .map(it -> it.setParentId(null))
                    .toList();
        }
        return groupRepository.findByName(query)
                .stream()
                .map(it -> it.setParentId(null))
                .toList();
    }

    public Group findGroup(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Группа с данными ИД не существует"))
                .setParentId(null);
    }

    @Transactional
    public Group updateGroup(Long id, UpdateGroupRequest request) {
        if (!id.equals(request.getId())) {
            throw new IllegalStateException("ИД в пути и в теле запроса не совпадают");
        }
        if (groupRepository.findByNameAndParentId(request.getName(), request.getParentId()).isPresent()) {
            throw new EntityAlreadyExistsException("Группа с данными именем и родительской номером группы уже существует");
        }

        var group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Группа с данными ИД не существует"));
        return groupRepository.save(
                        group.setParentId(request.getParentId() == null ? group.getParentId() : request.getParentId())
                                .setName(request.getName().isBlank() ? group.getName() : request.getName()));
    }

    @Transactional
    public void deleteGroup(Long id) {
        var children = groupRepository.findAllByParentId(id);
        if (!children.isEmpty()) {
            throw new IllegalStateException("У группы есть потомки");
        }
        groupRepository.deleteById(id);
    }

}
