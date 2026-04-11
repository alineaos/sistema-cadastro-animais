package shelter.animal.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import shelter.animal.dto.PetFilter;
import shelter.animal.dto.request.PetPatchRequest;
import shelter.animal.dto.request.PetPostRequest;
import shelter.animal.dto.response.PetGetResponse;
import shelter.animal.dto.response.PetPostResponse;
import shelter.animal.exceptions.NotFoundException;
import shelter.animal.mapper.PetMapper;
import shelter.animal.models.Address;
import shelter.animal.models.Pet;
import shelter.animal.repository.PetRepository;
import shelter.animal.repository.specifications.PetSpecification;

import java.util.List;

@RequiredArgsConstructor
@Validated
@Service
public class PetService {
    private final PetRepository repository;
    private final PetMapper mapper;

    public PetPostResponse save(@Valid PetPostRequest postRequest) {
        Pet petToSave = mapper.toPet(postRequest);

        Pet savedPet = repository.save(petToSave);

        return mapper.toPetPostResponse(savedPet);
    }

    public List<PetGetResponse> findAll() {
        List<Pet> petList = repository.findAll();

        return mapper.toPetGetResponseList(petList);
    }

    public PetGetResponse findByIdOrThrowNotFound(Long id) {
        Pet pet = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet não encontrado."));
        return mapper.toPetGetResponse(pet);
    }

    public List<PetGetResponse> findByCriteria(PetFilter filter) {
        List<Pet> petList = repository.findAll(
                PetSpecification.hasName(filter.name())
                        .and(PetSpecification.hasType(filter.type()))
                        .and(PetSpecification.hasSex(filter.sex()))
                        .and(PetSpecification.hasAddress(filter.address()))
                        .and(PetSpecification.hasAge(filter.age(), filter.ageUnit()))
                        .and(PetSpecification.hasWeight(filter.weight()))
                        .and(PetSpecification.hasBreed(filter.breed()))
                        .and(PetSpecification.hasCreatedAt(filter.createdAt()))
        );

        return mapper.toPetGetResponseList(petList);
    }

    public void delete(Long id) {
        Pet petToDelete = assertPetExists(id);
        repository.delete(petToDelete);
    }

    public void update(Long id, @Valid PetPatchRequest patchRequest) {
        Pet petFromDb = assertPetExists(id);

        Address addressToUpdate = Address.builder()
                .street(patchRequest.address().street().isBlank() ? petFromDb.getAddress().getStreet() : patchRequest.address().street())
                .number(patchRequest.address().number().isBlank() ? petFromDb.getAddress().getNumber() : patchRequest.address().number())
                .city(patchRequest.address().city().isBlank() ? petFromDb.getAddress().getCity() : patchRequest.address().city())
                .build();

        Pet petToUpdate = Pet.builder()
                .id(petFromDb.getId())
                .name(patchRequest.name().isBlank() ? petFromDb.getName() : patchRequest.name())
                .type(petFromDb.getType())
                .sex(petFromDb.getSex())
                .address(addressToUpdate)
                .age(patchRequest.age() == null ? petFromDb.getAge() : patchRequest.age())
                .ageUnit(patchRequest.ageUnit() == null ? petFromDb.getAgeUnit() : patchRequest.ageUnit())
                .weight(patchRequest.weight() == null ? petFromDb.getWeight() : patchRequest.weight())
                .breed(patchRequest.breed().isBlank() ? petFromDb.getBreed() : patchRequest.breed())
                .createdAt(petFromDb.getCreatedAt())
                .build();

        repository.save(petToUpdate);
    }

    private Pet assertPetExists(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("ID inválido. Pet não encontrado."));
    }
}
