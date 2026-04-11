package shelter.animal.models.enums;

import lombok.Getter;
import shelter.animal.exceptions.BusinessException;

@Getter
public enum PetSex {
    FEMALE(1, 'F', "Fêmea"),
    MALE(2, 'M', "Macho");

    private final int code;
    private final char abbreviation;
    private final String classification;

    PetSex(int code, char abbreviation, String classification) {
        this.code = code;
        this.abbreviation = abbreviation;
        this.classification = classification;
    }

    public static PetSex selectByCode(int codeInput){
        for (PetSex sex : PetSex.values()){
            if(codeInput == sex.getCode()){
                return sex;
            }
        }
        throw new BusinessException("O código [%d] não existe.".formatted(codeInput));
    }

    public static PetSex selectSex(char abbreviationOption){
        for (PetSex petSex : PetSex.values()){
            if(petSex.abbreviation == abbreviationOption){
                return petSex;
            }
        }
        throw new BusinessException("Opção inválida.");
    }
}
