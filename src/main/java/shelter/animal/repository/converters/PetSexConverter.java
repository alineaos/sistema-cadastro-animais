package shelter.animal.repository.converters;

import jakarta.persistence.AttributeConverter;
import shelter.animal.models.enums.PetSex;

public class PetSexConverter implements AttributeConverter<PetSex, Character> {

    @Override
    public Character convertToDatabaseColumn(PetSex petSex) {
        return petSex.getAbbreviation();
    }

    @Override
    public PetSex convertToEntityAttribute(Character dbData) {
        return PetSex.selectSex(dbData);
    }
}
