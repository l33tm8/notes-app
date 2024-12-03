package ru.ilya.notesapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ilya.notesapp.entity.Note;

public interface NoteRepository extends CrudRepository<Note, Long> {
}
