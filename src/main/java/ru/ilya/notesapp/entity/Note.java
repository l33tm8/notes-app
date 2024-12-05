package ru.ilya.notesapp.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 128)
    private String title;

    @Column(length = 4096)
    private String content;

    @Column
    private Date creationDate;

    @ManyToOne
    private User author;

    @ManyToOne
    private Priority priority;

    @ManyToMany
    @JoinTable(
            name = "notes_tags",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "notes_attachments",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private Set<Attachment> attachments = new HashSet<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getNotes().add(this);
    }

    public void removeTag(Tag tag) {
        Tag removeTag = tags.stream()
                .filter(t -> t.getId() == tag.getId())
                .findFirst()
                .orElse(null);
        if (removeTag != null) {
            removeTag.getNotes().remove(this);
            tags.remove(removeTag);
        }
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
        attachment.getNotes().add(this);
    }

    public void removeAttachment(Attachment attachment) {
        Attachment removeAttachment = attachments.stream()
                .filter(attachment1 -> attachment1.getId() == attachment.getId())
                .findFirst()
                .orElse(null);
        if (removeAttachment != null) {
            removeAttachment.getNotes().remove(this);
            attachments.remove(removeAttachment);
        }
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }
}
