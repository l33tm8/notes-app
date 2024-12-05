package ru.ilya.notesapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.ilya.notesapp.entity.Note;
import ru.ilya.notesapp.entity.User;

import java.util.List;

@Repository
@RepositoryRestResource(path = "notes")
public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findAllByAuthor(User author);
    List<Note> findNotesByTagsId(Long tagId);
}
