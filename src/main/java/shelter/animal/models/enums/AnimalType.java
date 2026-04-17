package shelter.animal.models.enums;

import lombok.Getter;
import shelter.animal.exceptions.BusinessException;

@Getter
public enum AnimalType {
    CAT(1, "Gato"),
    DOG(2, "Cachorro");

    private final int code;
    private final String label;

    AnimalType(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static AnimalType selectByCode(int codeInput) {
        for (AnimalType type : AnimalType.values()) {
            if (codeInput == type.getCode()) {
                return type;
            }
        }
        throw new BusinessException("O código [%d] não existe.".formatted(codeInput));
    }

    public static AnimalType selectByType(String typeInput) {
        for (AnimalType type : AnimalType.values()) {
            if (type.label.equalsIgnoreCase(typeInput)) {
                return type;
            }
        }
        return null;
    }
}
