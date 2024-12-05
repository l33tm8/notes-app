package ru.ilya.notesapp.service;

import org.springframework.stereotype.Service;
import ru.ilya.notesapp.entity.Tag;
import ru.ilya.notesapp.repository.TagRepository;

import java.util.Date;
import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    public List<Tag> getAllTags() {
        return (List<Tag>) tagRepository.findAll();
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public Tag save(Tag tag) {
        tag.setCreationDate(new Date());
        return tagRepository.save(tag);
    }

    public void deleteTagById(Long id) {
        tagRepository.deleteById(id);
    }
}
