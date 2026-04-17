package shelter.animal.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

public class InvalidRequestException extends ConstraintViolationException {
    public InvalidRequestException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message, constraintViolations);

        for (ConstraintViolation<?> constraintMessage : constraintViolations) {
            constraintMessage.getMessageTemplate();
        }

    }
}
