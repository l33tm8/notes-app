package ru.ilya.notesapp;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ilya.notesapp.entity.Note;
import ru.ilya.notesapp.entity.Tag;
import ru.ilya.notesapp.service.NoteService;
import ru.ilya.notesapp.service.TagService;

@SpringBootTest
public class NotesAndTagsTest {
    @Autowired
    private NoteService noteService;

    @Autowired
    private TagService tagService;

    @Test
    @Transactional
    public void AddTagToNote_ShouldReturnValidNoteTags() {
        Tag tag = new Tag();
        tag.setName("test");
        tag = tagService.save(tag);
        Note note = new Note();
        note.setContent("test");
        note.setTitle("test");
        note.addTag(tag);
        Note savedNote = noteService.save(note);
        Note returnNote = noteService.getNoteById(savedNote.getId());

        Assertions.assertEquals(1, returnNote.getTags().size());
    }


}
