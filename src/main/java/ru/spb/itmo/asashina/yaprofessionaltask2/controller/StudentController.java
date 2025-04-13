package ru.spb.itmo.asashina.yaprofessionaltask2.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.request.CreateStudentRequest;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.request.UpdateStudentRequest;
import ru.spb.itmo.asashina.yaprofessionaltask2.service.StudentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("")
    @Operation(
            summary = "Добавление студента с возможностью указания названия (name), контактных данных " +
                    "и идентификатора академической группы (group_id)",
            tags = "Student Controller")
    public ResponseEntity<?> createStudent(@RequestBody @Valid CreateStudentRequest request) {
        return ResponseEntity.ok(studentService.createStudent(request));
    }

    @GetMapping("")
    @Operation(summary = "Получение списка всех студентов", tags = "Student Controller")
    public ResponseEntity<?> getStudentsWithQuery(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(studentService.getAllStudents(query));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение студента по её идентификатору", tags = "Student Controller")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование метаданных о студенте по его идентификатору", tags = "Student Controller")
    public ResponseEntity<?> updateStudentById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateStudentRequest request) {

        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление студента по его идентификатору", tags = "Student Controller")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
