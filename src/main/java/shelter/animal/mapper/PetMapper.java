package shelter.animal.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import shelter.animal.dto.request.PetPostRequest;
import shelter.animal.dto.response.PetPostResponse;
import shelter.animal.models.Address;
import shelter.animal.models.Pet;
import shelter.animal.utils.AppConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface PetMapper {

    Pet toPet(PetPostRequest postRequest);

    PetPostResponse toPetPostResponse(Pet pet);

    @AfterMapping
    default void fillNullFields(@MappingTarget Pet pet) {
        if (pet.getName() == null || pet.getName().isBlank()) {
            pet.setName(AppConstants.NAO_INFORMADO);
        }
        if (pet.getAddress().getNumber() == null || pet.getAddress().getNumber().isEmpty()) {
            pet.setAddress(Address.builder()
                    .street(pet.getAddress().getStreet())
                    .number(AppConstants.NAO_INFORMADO)
                    .city(pet.getAddress().getCity())
                    .build());
        }
        if (pet.getBreed() == null || pet.getBreed().isEmpty()) {
            pet.setBreed(AppConstants.NAO_INFORMADO);
        }
    }
}
