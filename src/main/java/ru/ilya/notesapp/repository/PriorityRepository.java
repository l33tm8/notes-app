package ru.ilya.notesapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.ilya.notesapp.entity.Priority;

@Repository
@RepositoryRestResource(path = "priorities")
public interface PriorityRepository extends CrudRepository<Priority, Long> {
}
