package shelter.animal.repository.converters;

import jakarta.persistence.AttributeConverter;
import shelter.animal.models.enums.PetType;

public class PetTypeConverter implements AttributeConverter<PetType, String> {
    @Override
    public String convertToDatabaseColumn(PetType petType) {
        return petType.getLabel();
    }

    @Override
    public PetType convertToEntityAttribute(String dbData) {
        return PetType.selectByType(dbData);
    }
}
