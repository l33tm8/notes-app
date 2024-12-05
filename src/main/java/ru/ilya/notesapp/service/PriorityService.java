package ru.ilya.notesapp.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.ilya.notesapp.entity.Priority;
import ru.ilya.notesapp.repository.PriorityRepository;

import java.util.List;

@Service
public class PriorityService {
    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public Priority getById(long id) {
        return priorityRepository.findById(id).orElse(null);
    }

    public List<Priority> getAll() {
        return (List<Priority>) priorityRepository.findAll();
    }

    @Transactional
    public Priority save(Priority priority) {
        return priorityRepository.save(priority);
    }

    @Transactional
    public void delete(long id) {
        priorityRepository.deleteById(id);
    }
}
