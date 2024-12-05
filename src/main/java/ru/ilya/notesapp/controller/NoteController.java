package ru.ilya.notesapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ilya.notesapp.dto.SelectedOptionDto;
import ru.ilya.notesapp.entity.Attachment;
import ru.ilya.notesapp.entity.Note;
import ru.ilya.notesapp.entity.Tag;
import ru.ilya.notesapp.service.AttachmentService;
import ru.ilya.notesapp.service.NoteService;
import ru.ilya.notesapp.service.PriorityService;
import ru.ilya.notesapp.service.TagService;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    private final TagService tagService;

    private final AttachmentService attachmentService;

    private final PriorityService priorityService;

    public NoteController(NoteService noteService,
                          TagService tagService,
                          AttachmentService attachmentService,
                          PriorityService priorityService) {
        this.noteService = noteService;
        this.tagService = tagService;
        this.attachmentService = attachmentService;
        this.priorityService = priorityService;
    }

    @GetMapping
    public String getNotes(Model model) {
        List<Note> notes = noteService.getAllNotes();
        model.addAttribute("notes", notes);
        return "/note/notes";
    }

    @GetMapping("/{id}")
    public String getNote(Model model, @PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        List<Tag> allTags = tagService.getAllTags();
        List<Attachment> allAttachments = attachmentService.getAllAttachments();
        model.addAttribute("allTags", allTags);
        model.addAttribute("allAttachments", allAttachments);
        model.addAttribute("note", note);
        return "/note/note";
    }

    @GetMapping("/{id}/edit")
    public String editNote(Model model, @PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "note/edit";
    }

    @PostMapping("/{id}/edit")
    public String editNote(@PathVariable Long id, @ModelAttribute Note note) {
        note.setId(id);
        noteService.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/create")
    public String createNote(Model model) {
        Note note = new Note();

        model.addAttribute("allPriorities", priorityService.getAll());
        model.addAttribute("note", note);
        return "note/create";
    }

    @PostMapping
    public String saveNote(@ModelAttribute Note note) {
        noteService.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/{id}/delete")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteNoteById(id);
        return "redirect:/notes";
    }

    @PostMapping("/{id}/addTag")
    public String addTag(@ModelAttribute SelectedOptionDto selectedOptionDto, @PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        noteService.addTag(note, selectedOptionDto.getSelectedId());
        return "redirect:/notes/{id}";
    }

    @PostMapping("/{id}/tags/{tagId}/remove")
    public String removeTag(@PathVariable Long id, @PathVariable Long tagId) {
        noteService.removeTag(noteService.getNoteById(id), tagId);
        return "redirect:/notes/{id}";
    }

    @PostMapping("/{id}/addAttachment")
    public String addAttachment(@ModelAttribute SelectedOptionDto selectedOptionDto, @PathVariable Long id) {
        Note note = noteService.getNoteById(id);
        noteService.addAttachment(note, selectedOptionDto.getSelectedId());
        return "redirect:/notes/{id}";
    }

    @PostMapping("/{id}/attachments/remove")
    public String removeAttachment(@ModelAttribute SelectedOptionDto selectedOptionDto, @PathVariable Long id) {
        noteService.removeAttachment(noteService.getNoteById(id), selectedOptionDto.getSelectedId());
        return "redirect:/notes/{id}";
    }
}
