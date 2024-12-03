package ru.ilya.notesapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "priorities")
public class Priority {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 50)
    private String name;

    @Column(length = 512)
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private Color color;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
