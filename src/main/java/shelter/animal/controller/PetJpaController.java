package shelter.animal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import shelter.animal.dto.AddressFilter;
import shelter.animal.dto.PetFilter;
import shelter.animal.dto.request.AddressPatchRequest;
import shelter.animal.dto.request.AddressPostRequest;
import shelter.animal.dto.request.PetPatchRequest;
import shelter.animal.dto.request.PetPostRequest;
import shelter.animal.dto.response.PetGetResponse;
import shelter.animal.dto.response.PetPostResponse;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;
import shelter.animal.service.PetJpaService;
import shelter.animal.utils.RequestDtoValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    public List<PetGetResponse> findAll() {
        return service.findAll();
    }

    public PetGetResponse findById(Long id) {
        return service.findByIdOrThrowNotFound(id);
    }

    public List<PetGetResponse> findByCriteria(Map<String, Object> params) {

        AddressFilter addressFilter = null;
        if (params.containsKey("city")) {
            addressFilter = AddressFilter.builder()
                    .street((String) params.get("street"))
                    .number((String) params.get("number"))
                    .city((String) params.get("city"))
                    .build();
        }

        PetFilter petFilter = PetFilter.builder()
                .name((String) params.get("name"))
                .type((PetType) params.get("type"))
                .sex((PetSex) params.get("sex"))
                .address(addressFilter)
                .age((Double) params.get("age"))
                .ageUnit((AgeUnit) params.get("ageUnit"))
                .weight((Double) params.get("weight"))
                .breed((String) params.get("breed"))
                .createdAt((LocalDate) params.get("createdAt"))
                .build();

        return service.findByCriteria(petFilter);
    }

    public void delete(Long id) {
        service.delete(id);
    }

    public void update(Long id, String name, String street, String number, String city,
                       Double age, AgeUnit ageUnit, Double weight, String breed) {


        AddressPatchRequest addressPatchRequest = AddressPatchRequest.builder()
                .street(street)
                .number(number)
                .city(city)
                .build();

        PetPatchRequest petPatchRequest = PetPatchRequest.builder()
                .name(name)
                .address(addressPatchRequest)
                .age(age)
                .ageUnit(ageUnit)
                .weight(weight)
                .breed(breed)
                .build();


        service.update(id, petPatchRequest);
    }
}
