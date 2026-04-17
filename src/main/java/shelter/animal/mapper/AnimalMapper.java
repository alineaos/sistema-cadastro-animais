package shelter.animal.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import shelter.animal.dto.request.AnimalPostRequest;
import shelter.animal.dto.response.AnimalGetResponse;
import shelter.animal.dto.response.AnimalPostResponse;
import shelter.animal.models.Address;
import shelter.animal.models.Animal;
import shelter.animal.utils.AppConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class})
public interface AnimalMapper {

    Animal toAnimal(AnimalPostRequest postRequest);

    AnimalPostResponse toAnimalPostResponse(Animal animal);

    AnimalGetResponse toAnimalGetResponse(Animal animal);

    List<AnimalGetResponse> toAnimalGetResponseList(List<Animal> animalList);

    @AfterMapping
    default void fillNullFields(@MappingTarget Animal animal) {
        if (animal.getName() == null || animal.getName().isBlank()) {
            animal.setName(AppConstants.NAO_INFORMADO);
        }
        if (animal.getAddress().getNumber() == null || animal.getAddress().getNumber().isEmpty()) {
            animal.setAddress(Address.builder()
                    .street(animal.getAddress().getStreet())
                    .number(AppConstants.NAO_INFORMADO)
                    .city(animal.getAddress().getCity())
                    .build());
        }
        if (animal.getBreed() == null || animal.getBreed().isEmpty()) {
            animal.setBreed(AppConstants.NAO_INFORMADO);
        }
    }
}
