package ru.ilya.notesapp.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ilya.notesapp.service.AttachmentService;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @GetMapping
    public String listAttachments(Model model) {
        model.addAttribute("attachments", attachmentService.getAllAttachments());
        return "attachment/attachments";
    }

    @GetMapping("/{id}/view")
    @ResponseBody
    public ResponseEntity<FileSystemResource> viewAttachment(@PathVariable long id) {
        FileSystemResource fileSystemResource = attachmentService.getAttachmentFile(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""
                + fileSystemResource.getFilename() + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(getContentType(Objects.requireNonNull(fileSystemResource.getFilename())))
                .body(fileSystemResource);
    }

    @GetMapping("/upload")
    public String upload() {
        return "attachment/upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        attachmentService.save(file);
        return "redirect:/attachments";
    }

    private MediaType getContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        return switch (extension.toLowerCase()) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}
