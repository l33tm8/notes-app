package ru.ilya.notesapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ilya.notesapp.entity.Tag;

public interface TagRepository extends CrudRepository<Tag, Long> {
}
