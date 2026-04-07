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

    public Long parseLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e){
            throw new BusinessException("'%s' não é um número.".formatted(input));
        }
    }
}
