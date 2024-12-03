package ru.ilya.notesapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ilya.notesapp.entity.Priority;

public interface PriorityRepository extends CrudRepository<Priority, Long> {
}
