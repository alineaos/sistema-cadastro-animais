package shelter.animal.repository.converters;

import jakarta.persistence.AttributeConverter;
import shelter.animal.models.Address;

public class AddressConverter implements AttributeConverter<Address, String> {
    private final String DELIMITER = ",";
    @Override
    public String convertToDatabaseColumn(Address address) {
        return address.getStreet() + DELIMITER + address.getNumber() + DELIMITER + address.getCity();
    }

    @Override
    public Address convertToEntityAttribute(String dbData) {
        String[] data = dbData.split(DELIMITER);

        return Address.builder()
                .street(data[0])
                .number(data[1])
                .city(data[2])
                .build();
    }
}
