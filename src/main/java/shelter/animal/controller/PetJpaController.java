package shelter.animal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import shelter.animal.dto.request.AddressPostRequest;
import shelter.animal.dto.request.PetPostRequest;
import shelter.animal.dto.response.PetGetResponse;
import shelter.animal.dto.response.PetPostResponse;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;
import shelter.animal.service.PetJpaService;
import shelter.animal.utils.RequestDtoValidator;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PetJpaController {
    private final PetJpaService service;
    private final RequestDtoValidator requestValidator;

    public PetPostResponse save(String name, PetType petType, PetSex petSex, String street, String number,
                                String city, Double age, AgeUnit ageUnit, Double weight, String breed) {
        AddressPostRequest addressPostRequest = AddressPostRequest.builder()
                .street(street)
                .number(number)
                .city(city)
                .build();

        PetPostRequest petPostRequest = PetPostRequest.builder()
                .name(name)
                .type(petType)
                .sex(petSex)
                .address(addressPostRequest)
                .age(age)
                .ageUnit(ageUnit)
                .weight(weight)
                .breed(breed)
                .build();

        requestValidator.validateRequest(petPostRequest);

        return service.save(petPostRequest);

    }

    public List<PetGetResponse> findAll(){
        return service.findAll();
    }

    public PetGetResponse findById(Long id){
        return service.findByIdOrThrowNotFound(id);
    }
}
