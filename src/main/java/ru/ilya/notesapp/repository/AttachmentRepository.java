package ru.ilya.notesapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ilya.notesapp.entity.Attachment;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
}
