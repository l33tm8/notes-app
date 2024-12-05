package ru.ilya.notesapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.ilya.notesapp.entity.Tag;

import java.util.List;

@Repository
@RepositoryRestResource(path = "tags")
public interface TagRepository extends CrudRepository<Tag, Long> {
    List<Tag> findTagByNotesId(Long noteId);
}
