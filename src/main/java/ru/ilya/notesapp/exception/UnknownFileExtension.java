package ru.ilya.notesapp.exception;

public class UnknownFileExtension extends RuntimeException {
    public UnknownFileExtension() {
        super("Unknown file extension");
    }
}
