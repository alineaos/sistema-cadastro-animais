package shelter.animal.models.enums;

import lombok.Getter;
import shelter.animal.exceptions.BusinessException;

@Getter
public enum AnimalSex {
    FEMALE(1, 'F', "Fêmea"),
    MALE(2, 'M', "Macho");

    private final int code;
    private final char abbreviation;
    private final String classification;

    AnimalSex(int code, char abbreviation, String classification) {
        this.code = code;
        this.abbreviation = abbreviation;
        this.classification = classification;
    }

    public static AnimalSex selectByCode(int codeInput) {
        for (AnimalSex sex : AnimalSex.values()) {
            if (codeInput == sex.getCode()) {
                return sex;
            }
        }
        throw new BusinessException("O código [%d] não existe.".formatted(codeInput));
    }

    public static AnimalSex selectSex(char abbreviationOption) {
        for (AnimalSex animalSex : AnimalSex.values()) {
            if (animalSex.abbreviation == abbreviationOption) {
                return animalSex;
            }
        }
        throw new BusinessException("Opção inválida.");
    }
}
