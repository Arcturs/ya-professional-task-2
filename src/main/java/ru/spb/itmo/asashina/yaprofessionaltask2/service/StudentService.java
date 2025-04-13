package ru.spb.itmo.asashina.yaprofessionaltask2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spb.itmo.asashina.yaprofessionaltask2.exception.EntityAlreadyExistsException;
import ru.spb.itmo.asashina.yaprofessionaltask2.exception.EntityDoesNotExistException;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.entity.Group;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.entity.Student;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.request.CreateStudentRequest;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.request.UpdateStudentRequest;
import ru.spb.itmo.asashina.yaprofessionaltask2.repository.GroupRepository;
import ru.spb.itmo.asashina.yaprofessionaltask2.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public Student createStudent(CreateStudentRequest request) {
        if (studentRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("Студент с данной почтой уже существует");
        }
        if (studentRepository.findByNameAndGroupId(request.getName(), request.getGroupId()).isPresent()) {
            throw new EntityAlreadyExistsException("Студент с данными именем и номером группы уже существует");
        }
        var group = groupRepository.findById(request.getGroupId());
        if (group.isEmpty()) {
            throw new EntityDoesNotExistException("Группа с данными номером не существует");
        }
        return studentRepository.save(
                new Student()
                        .setEmail(request.getEmail())
                        .setName(request.getName())
                        .setGroupId(request.getGroupId()))
                .setEmail(null);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(el -> el.setEmail(null))
                .toList();
    }

    public List<Student> getAllStudents(String query) {
        if (query == null || query.isEmpty()) {
            return getAllStudents();
        }
        return studentRepository.findAllByQuery(query)
                .stream()
                .map(el -> el.setEmail(null))
                .toList();
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Студент с данными ИД не существует"))
                .setEmail(null);
    }

    @Transactional
    public Student updateStudent(Long id, UpdateStudentRequest request) {
        if (!id.equals(request.getId())) {
            throw new IllegalStateException("ИД в пути и в теле запроса не совпадают");
        }
        if (studentRepository.findByNameAndGroupId(request.getName(), request.getGroupId()).isPresent()) {
            throw new EntityAlreadyExistsException("Студент с данными именем и номером группы уже существует");
        }

        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Студент с данными ИД не существует"));
        return studentRepository.save(
                    student.setGroupId(request.getGroupId() == null ? student.getGroupId() : request.getGroupId())
                        .setName(request.getName().isBlank() ? student.getName() : request.getName()))
                .setEmail(null);
    }

    @Transactional
    public void deleteStudent(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityDoesNotExistException("Студент с данными ИД не существует"));
        studentRepository.deleteById(id);
    }

}
