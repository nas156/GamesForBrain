package ua.project.games.service.fileStorage;

public class FileFormatException extends RuntimeException{

    public FileFormatException(String message) {
        super(message);
    }

    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
