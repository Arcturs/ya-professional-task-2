package ru.spb.itmo.asashina.yaprofessionaltask2.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spb.itmo.asashina.yaprofessionaltask2.model.entity.Group;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByNameAndParentId(String groupName, Long parentId);

    @Query("""
        select g.*
        from groups g
        where g.name like concat('%', :query, '%') 
        """)
    List<Group> findByName(String name);

    List<Group> findAll();

    @Query("""
        select g.*
        from groups g
        where g.id = :parentId
        """)
    Optional<Group> findByParentId(Long parentId);

    List<Group> findAllByParentId(Long parentId);

}
