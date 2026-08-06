package org.swetlokognatsk.earking_out.inftrastructure;

// TODO where exceptions should be with clean ddd architecture?
public class EventSavingException extends Exception {
    public EventSavingException() {
    }

    public EventSavingException(String message) {
        super(message);
    }

    public EventSavingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventSavingException(Throwable cause) {
        super(cause);
    }
}
