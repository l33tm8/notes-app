package ru.ilya.notesapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.ilya.notesapp.entity.Attachment;

@Repository
@RepositoryRestResource(path = "attachments")
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

}
