package shelter.animal.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import shelter.animal.dto.request.PetPostRequest;
import shelter.animal.dto.response.PetGetResponse;
import shelter.animal.dto.response.PetPostResponse;
import shelter.animal.mapper.PetMapper;
import shelter.animal.models.Pet;
import shelter.animal.repository.PetJpaRepository;

import java.util.List;

@RequiredArgsConstructor
@Validated
@Service
public class PetJpaService {
    private final PetJpaRepository repository;
    private final PetMapper mapper;

    public PetPostResponse save(@Valid PetPostRequest postRequest) {
        Pet petToSave = mapper.toPet(postRequest);

        Pet savedPet = repository.save(petToSave);

        return mapper.toPetPostResponse(savedPet);
    }

    public List<PetGetResponse> findAll(){
        List<Pet> petList = repository.findAll();

        return mapper.toPetGetResponseList(petList);
    }
}
