package iomanager;

public class FileNotExistsException extends Exception {
    public FileNotExistsException() {
        super();
    }

    public FileNotExistsException(String message) {
        super(message);
    }

    public FileNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
