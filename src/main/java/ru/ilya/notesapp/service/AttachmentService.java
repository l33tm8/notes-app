package ru.ilya.notesapp.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ilya.notesapp.entity.Attachment;
import ru.ilya.notesapp.entity.FileType;
import ru.ilya.notesapp.exception.NotFoundException;
import ru.ilya.notesapp.exception.UnknownFileExtension;
import ru.ilya.notesapp.repository.AttachmentRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

@Service
public class AttachmentService {
    @Value("${application.upload-path}")
    private String uploadPath;

    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public List<Attachment> getAllAttachments() {
        return (List<Attachment>) attachmentRepository.findAll();
    }

    public Attachment getAttachmentById(long id) {
        return attachmentRepository.findById(id).orElse(null);
    }


    @Transactional
    public Attachment save(MultipartFile file) throws IOException {
        String filePath = uploadPath + file.getOriginalFilename();
        String extension = filePath.substring(filePath.lastIndexOf("."));

        Attachment attachment = new Attachment();

        switch (extension) {
            case ".jpg":
            case ".jpeg":
            case ".png":
            case ".gif": {
                attachment.setFiletype(FileType.TYPE_IMAGE);
                break;
            }
            case ".mp4":
            case ".mov":
            case ".avi": {
                attachment.setFiletype(FileType.TYPE_VIDEO);
                break;
            }
            case ".mp3":
            case ".wav": {
                attachment.setFiletype(FileType.TYPE_AUDIO);
                break;
            }
            default:
                throw new UnknownFileExtension();
        }
        Path path = Paths.get(uploadPath);
        Path destinationFile = path.resolve(Objects.requireNonNull(file.getOriginalFilename()))
                .normalize().toAbsolutePath();
        copyFileToPath(file, destinationFile);
        attachment.setFilename(file.getOriginalFilename());
        attachment.setFilepath(filePath);
        return attachmentRepository.save(attachment);
    }

    public FileSystemResource getAttachmentFile(long id) {
        Attachment attachment = attachmentRepository.findById(id).orElse(null);
        if (attachment == null) {
            throw new NotFoundException("attachment not found");
        }
        Path path = Paths.get(uploadPath);
        Path destinationFile = path.resolve(attachment.getFilename());
        return new FileSystemResource(destinationFile.toFile());
    }


    public void delete(long id) {
        attachmentRepository.deleteById(id);
    }

    private void copyFileToPath(MultipartFile file, Path destinationPath) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
