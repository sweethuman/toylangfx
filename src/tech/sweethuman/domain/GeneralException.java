package tech.sweethuman.domain;

public class GeneralException extends Exception {
    String message;

    public GeneralException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error: " + this.message;
    }
}
