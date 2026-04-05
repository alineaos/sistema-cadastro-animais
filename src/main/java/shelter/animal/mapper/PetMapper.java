package shelter.animal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import shelter.animal.dto.request.PetPostRequest;
import shelter.animal.dto.response.PetPostResponse;
import shelter.animal.models.Pet;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface PetMapper {

    Pet toPet(PetPostRequest postRequest);

    PetPostResponse toPetPostResponse(Pet pet);
}
