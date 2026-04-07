package shelter.animal.utils;

import org.springframework.stereotype.Component;
import shelter.animal.exceptions.BusinessException;

@Component
public class InputValidator {
    public Integer parseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            throw new BusinessException("'%s' não é um número inteiro.".formatted(input));
        }
    }

    public Double parseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e){
            throw new BusinessException("'%s' não é um número.".formatted(input));
        }
    }
}
