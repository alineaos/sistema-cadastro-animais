package shelter.animal.utils;

import org.springframework.stereotype.Component;
import shelter.animal.exceptions.BusinessException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class InputValidator {
    public Integer parseInteger(String input) {
        try {
            Integer formattedInput = Integer.parseInt(input);
            if (formattedInput < 0) {
                throw new BusinessException("'%d' é um número negativo.".formatted(formattedInput));
            }
            return formattedInput;
        } catch (NumberFormatException e) {
            throw new BusinessException("'%s' não é um número inteiro.".formatted(input));
        }
    }

    public Double parseDouble(String input) {
        try {
            Double formattedInput = Double.parseDouble(input);
            if (formattedInput < 0.0) {
                throw new BusinessException("'%f' é um número negativo.".formatted(formattedInput));
            }
            return formattedInput;
        } catch (NumberFormatException e) {
            throw new BusinessException("'%s' não é um número.".formatted(input));
        }
    }

    public Long parseLong(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new BusinessException("'%s' não é um número.".formatted(input));
        }
    }

    public LocalDate parseDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            return LocalDate.parse(input, formatter);

        } catch (DateTimeParseException e) {
            throw new BusinessException(input + " Não é uma data válida.");
        }
    }
}
