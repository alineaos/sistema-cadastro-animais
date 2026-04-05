package shelter.animal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import shelter.animal.dto.request.AddressPostRequest;
import shelter.animal.models.Address;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    Address toAddress(AddressPostRequest postRequest);
}
