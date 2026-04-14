package shelter.animal.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import shelter.animal.dto.AnimalFilter;
import shelter.animal.dto.request.AnimalPatchRequest;
import shelter.animal.dto.request.AnimalPostRequest;
import shelter.animal.dto.response.AnimalGetResponse;
import shelter.animal.dto.response.AnimalPostResponse;
import shelter.animal.exceptions.NotFoundException;
import shelter.animal.mapper.AnimalMapper;
import shelter.animal.models.Address;
import shelter.animal.models.Animal;
import shelter.animal.repository.AnimalRepository;
import shelter.animal.repository.specifications.AnimalSpecification;

import java.util.List;

@RequiredArgsConstructor
@Validated
@Service
public class AnimalService {
    private final AnimalRepository repository;
    private final AnimalMapper mapper;

    public AnimalPostResponse save(@Valid AnimalPostRequest postRequest) {
        Animal animalToSave = mapper.toAnimal(postRequest);

        Animal savedAnimal = repository.save(animalToSave);

        return mapper.toAnimalPostResponse(savedAnimal);
    }

    public List<AnimalGetResponse> findAll() {
        List<Animal> animalList = repository.findAll();

        return mapper.toAnimalGetResponseList(animalList);
    }

    public AnimalGetResponse findByIdOrThrowNotFound(Long id) {
        Animal animal = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Animal não encontrado."));
        return mapper.toAnimalGetResponse(animal);
    }

    public List<AnimalGetResponse> findByCriteria(AnimalFilter filter) {
        List<Animal> animalList = repository.findAll(
                AnimalSpecification.hasName(filter.name())
                        .and(AnimalSpecification.hasType(filter.type()))
                        .and(AnimalSpecification.hasSex(filter.sex()))
                        .and(AnimalSpecification.hasAddress(filter.address()))
                        .and(AnimalSpecification.hasAge(filter.age(), filter.ageUnit()))
                        .and(AnimalSpecification.hasWeight(filter.weight()))
                        .and(AnimalSpecification.hasBreed(filter.breed()))
                        .and(AnimalSpecification.hasCreatedAt(filter.createdAt()))
        );

        return mapper.toAnimalGetResponseList(animalList);
    }

    public void delete(Long id) {
        Animal animalToDelete = assertAnimalExists(id);
        repository.delete(animalToDelete);
    }

    public void update(Long id, @Valid AnimalPatchRequest patchRequest) {
        Animal animalFromDb = assertAnimalExists(id);

        Address addressToUpdate = Address.builder()
                .street(patchRequest.address().street().isBlank() ? animalFromDb.getAddress().getStreet() : patchRequest.address().street())
                .number(patchRequest.address().number().isBlank() ? animalFromDb.getAddress().getNumber() : patchRequest.address().number())
                .city(patchRequest.address().city().isBlank() ? animalFromDb.getAddress().getCity() : patchRequest.address().city())
                .build();

        Animal animalToUpdate = Animal.builder()
                .id(animalFromDb.getId())
                .name(patchRequest.name().isBlank() ? animalFromDb.getName() : patchRequest.name())
                .type(animalFromDb.getType())
                .sex(animalFromDb.getSex())
                .address(addressToUpdate)
                .age(patchRequest.age() == null ? animalFromDb.getAge() : patchRequest.age())
                .ageUnit(patchRequest.ageUnit() == null ? animalFromDb.getAgeUnit() : patchRequest.ageUnit())
                .weight(patchRequest.weight() == null ? animalFromDb.getWeight() : patchRequest.weight())
                .breed(patchRequest.breed().isBlank() ? animalFromDb.getBreed() : patchRequest.breed())
                .createdAt(animalFromDb.getCreatedAt())
                .build();

        repository.save(animalToUpdate);
    }

    private Animal assertAnimalExists(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("ID inválido. Animal não encontrado."));
    }
}
