package shelter.animal.repository.converters;

import jakarta.persistence.AttributeConverter;
import shelter.animal.utils.AppConstants;

public class AgeConverter implements AttributeConverter<Integer, String> {

    @Override
    public String convertToDatabaseColumn(Integer value) {
        if (value == null) return AppConstants.NAO_INFORMADO;
        return value.toString();
    }

    @Override
    public Integer convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.equalsIgnoreCase(AppConstants.NAO_INFORMADO)) return null;
        return Integer.parseInt(dbData);
    }
}
