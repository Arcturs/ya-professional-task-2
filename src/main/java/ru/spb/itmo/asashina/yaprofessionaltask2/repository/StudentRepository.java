package ru.spb.itmo.asashina.yaprofessionaltask2.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByNameAndGroupId(String name, Long groupId);

    List<Student> findAll();

    @Query("""
        select s.*
        from students s
        join groups g on g.id = s.group_id
        where g.name like concat('%', :query, '%') 
           or s.name like concat('%', :query, '%')
        """)
    List<Student> findAllByQuery(String query);

}
