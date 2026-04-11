package shelter.animal.models.enums;

import lombok.Getter;
import shelter.animal.exceptions.BusinessException;

@Getter
public enum PetType {
    CAT(1, "Gato"),
    DOG(2, "Cachorro");

    private final int code;
    private final String label;

    PetType(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public static PetType selectByCode(int codeInput){
        for (PetType classification : PetType.values()){
            if(codeInput == classification.getCode()){
                return classification;
            }
        }
        throw new BusinessException("O código [%d] não existe.".formatted(codeInput));
    }

    public static PetType selectByType(String classificationInput){
        for (PetType classification : PetType.values()){
            if(classification.label.equalsIgnoreCase(classificationInput)){
                return classification;
            }
        }
        return null;
    }
}
