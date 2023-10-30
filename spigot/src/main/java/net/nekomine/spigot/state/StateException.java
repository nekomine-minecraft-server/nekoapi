package net.nekomine.spigot.state;

public class StateException extends RuntimeException {

    private final String message;

    public StateException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
