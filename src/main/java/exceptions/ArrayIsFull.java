package exceptions;

import java.util.Arrays;

public class ArrayIsFull extends Exception {

    private final String textOfException = "ArrayIsFull Exception here.";
    private final String textOfLocalizedException = "ArrayIsFull Localized Exception here.";

    public ArrayIsFull(String message) {
        super(message);
        setStackTrace(getStackTrace());
    }

    public ArrayIsFull(String message, Throwable cause) {
        super(message, cause);
        setStackTrace(getStackTrace());
    }

    @Override
    public String getMessage() {
        return textOfException + " - " + super.getMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        Throwable cause = super.getCause();
        return (cause == null ? new Throwable(getLocalizedMessage()) : new Throwable(getLocalizedMessage(), cause));
    }

    @Override
    public String getLocalizedMessage() {
        return textOfLocalizedException + " - " + getMessage();
    }

    @Override
    public String toString() {
        String className = getClass().getName();
        if (getMessage() != null)
            return textOfException + " - " + className + ": " + getMessage();
        else return textOfException + " - " + getClass().getName();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        StackTraceElement stackTraceElement = new StackTraceElement(getClass().getName(),
                "PlayingWithSomeThrowableInheritance", "IgnoreThisLine",
                Thread.currentThread().getStackTrace()[1].getLineNumber());
        StackTraceElement[] stackTraceElements = Arrays.copyOf(stackTrace, stackTrace.length + 1);
        stackTraceElements[stackTrace.length] = stackTraceElement;
        super.setStackTrace(stackTraceElements);
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        Throwable throwable = super.initCause(cause);
        return new Throwable("Testing " + getMessage(), throwable.getCause());
    }
}
