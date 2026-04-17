package shelter.animal.repository.converters;

import jakarta.persistence.AttributeConverter;
import shelter.animal.models.enums.AnimalSex;

public class AnimalSexConverter implements AttributeConverter<AnimalSex, Character> {
    @Override
    public Character convertToDatabaseColumn(AnimalSex animalSex) {
        return animalSex.getAbbreviation();
    }

    @Override
    public AnimalSex convertToEntityAttribute(Character dbData) {
        return AnimalSex.selectSex(dbData);
    }
}
