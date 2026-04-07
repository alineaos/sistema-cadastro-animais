package shelter.animal.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shelter.animal.exceptions.InvalidRequestException;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class RequestDtoValidator {
    private final Validator validator;

    public <T> void validateRequest(T requestDto){
        Set<ConstraintViolation<T>> violations = validator.validate(requestDto);
        if (!violations.isEmpty()){
            throw new InvalidRequestException("Dados inválidos:", violations);
        }
    }
}
