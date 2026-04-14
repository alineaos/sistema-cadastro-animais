package shelter.animal.repository.converters;

import jakarta.persistence.AttributeConverter;
import shelter.animal.models.enums.AnimalType;

public class AnimalTypeConverter implements AttributeConverter<AnimalType, String> {
    @Override
    public String convertToDatabaseColumn(AnimalType animalType) {
        return animalType.getLabel();
    }

    @Override
    public AnimalType convertToEntityAttribute(String dbData) {
        return AnimalType.selectByType(dbData);
    }
}
