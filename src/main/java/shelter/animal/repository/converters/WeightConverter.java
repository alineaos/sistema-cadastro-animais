package shelter.animal.repository.converters;

import jakarta.persistence.AttributeConverter;
import shelter.animal.utils.AppConstants;

public class WeightConverter implements AttributeConverter<Double, String> {

    @Override
    public String convertToDatabaseColumn(Double value) {
        if (value == null) return AppConstants.NAO_INFORMADO;
        return value.toString();
    }

    @Override
    public Double convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.equalsIgnoreCase(AppConstants.NAO_INFORMADO)) return null;
        return Double.parseDouble(dbData);
    }
}
