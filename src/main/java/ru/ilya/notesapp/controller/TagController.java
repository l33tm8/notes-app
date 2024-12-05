package ru.ilya.notesapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ilya.notesapp.entity.Tag;
import ru.ilya.notesapp.service.TagService;

@Controller
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public String getTags(Model model) {
        model.addAttribute("tags", tagService.getAllTags());
        return "/tag/tags";
    }

    @GetMapping("/{id}")
    public String getTag(@PathVariable long id, Model model) {
        model.addAttribute("tag", tagService.getTagById(id));
        return "/tag/tag";
    }

    @GetMapping("/create")
    public String createTag(Model model) {
        model.addAttribute("tag", new Tag());
        return "/tag/create";
    }

    @PostMapping
    public String createTag(@ModelAttribute Tag tag) {
        tagService.save(tag);
        return "redirect:/tags";
    }

}
