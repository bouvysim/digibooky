package be.thebest.domain.exception;

public class MemberValidationException extends RuntimeException {

    public MemberValidationException(String errorMessage) {
        super(errorMessage);
    }
}
