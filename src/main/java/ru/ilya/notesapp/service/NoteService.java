package ru.ilya.notesapp.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.ilya.notesapp.entity.Attachment;
import ru.ilya.notesapp.entity.Note;
import ru.ilya.notesapp.entity.Tag;
import ru.ilya.notesapp.entity.User;
import ru.ilya.notesapp.exception.NotFoundException;
import ru.ilya.notesapp.repository.AttachmentRepository;
import ru.ilya.notesapp.repository.NoteRepository;
import ru.ilya.notesapp.repository.TagRepository;
import ru.ilya.notesapp.repository.UserRepository;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final AttachmentRepository attachmentRepository;

    public NoteService(NoteRepository noteRepository,
                       UserRepository userRepository,
                       TagRepository tagRepository,
                       AttachmentRepository attachmentRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.attachmentRepository = attachmentRepository;
    }

    public List<Note> getAllNotes() {
        return (List<Note>) noteRepository.findAll();
    }

    public List<Note> getAuthorNotes(String username) {
        User user = userRepository.findByUsername(username);
        return noteRepository.findAllByAuthor(user);
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    @Transactional
    public Note save(Note note) {
        return noteRepository.save(note);
    }

    @Transactional
    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }

    @Transactional
    public void addTag(Note note, long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new NotFoundException("Tag not found"));
        note.addTag(tag);
        noteRepository.save(note);
    }

    @Transactional
    public void removeTag(Note note, long tagId) throws NotFoundException {
        Tag tag = note.getTags().stream()
                .filter(t -> t.getId() == tagId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("tag not found in this note"));

        note.removeTag(tag);
    }

    @Transactional
    public void addAttachment(Note note, long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(
                () -> new NotFoundException("Attachment not found"));

        note.addAttachment(attachment);
        noteRepository.save(note);
    }

    public void removeAttachment(Note note, long attachmentId) {
        Attachment attachment = note.getAttachments().stream()
                .filter(at -> at.getId() == attachmentId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("attachment not found in this note"));

        note.removeAttachment(attachment);
    }

}
