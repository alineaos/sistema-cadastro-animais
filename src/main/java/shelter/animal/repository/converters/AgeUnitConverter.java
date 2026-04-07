package shelter.animal.repository.converters;

import jakarta.persistence.AttributeConverter;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.utils.AppConstants;

public class AgeUnitConverter implements AttributeConverter<AgeUnit, String> {
    @Override
    public String convertToDatabaseColumn(AgeUnit ageUnit) {
        if (ageUnit == null) return AppConstants.NAO_INFORMADO;
        return ageUnit.getLabel();
    }

    @Override
    public AgeUnit convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.equalsIgnoreCase(AppConstants.NAO_INFORMADO)) return null;
        return AgeUnit.selectByLabel(dbData);
    }
}
