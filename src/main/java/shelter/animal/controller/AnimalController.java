package shelter.animal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import shelter.animal.dto.AddressFilter;
import shelter.animal.dto.AnimalFilter;
import shelter.animal.dto.request.AddressPatchRequest;
import shelter.animal.dto.request.AddressPostRequest;
import shelter.animal.dto.request.AnimalPatchRequest;
import shelter.animal.dto.request.AnimalPostRequest;
import shelter.animal.dto.response.AnimalGetResponse;
import shelter.animal.dto.response.AnimalPostResponse;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.AnimalSex;
import shelter.animal.models.enums.AnimalType;
import shelter.animal.service.AnimalService;
import shelter.animal.utils.RequestDtoValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AnimalController {
    private final AnimalService service;
    private final RequestDtoValidator requestValidator;

    public AnimalPostResponse save(String name, AnimalType animalType, AnimalSex animalSex, String street, String number,
                                   String city, Double age, AgeUnit ageUnit, Double weight, String breed) {
        AddressPostRequest addressPostRequest = AddressPostRequest.builder()
                .street(street)
                .number(number)
                .city(city)
                .build();

        AnimalPostRequest animalPostRequest = AnimalPostRequest.builder()
                .name(name)
                .type(animalType)
                .sex(animalSex)
                .address(addressPostRequest)
                .age(age)
                .ageUnit(ageUnit)
                .weight(weight)
                .breed(breed)
                .build();

        requestValidator.validateRequest(animalPostRequest);

        return service.save(animalPostRequest);

    }

    public List<AnimalGetResponse> findAll() {
        return service.findAll();
    }

    public AnimalGetResponse findById(Long id) {
        return service.findByIdOrThrowNotFound(id);
    }

    public List<AnimalGetResponse> findByCriteria(Map<String, Object> params) {

        AddressFilter addressFilter = null;
        if (params.containsKey("city")) {
            addressFilter = AddressFilter.builder()
                    .street((String) params.get("street"))
                    .number((String) params.get("number"))
                    .city((String) params.get("city"))
                    .build();
        }

        AnimalFilter animalFilter = AnimalFilter.builder()
                .name((String) params.get("name"))
                .type((AnimalType) params.get("type"))
                .sex((AnimalSex) params.get("sex"))
                .address(addressFilter)
                .age((Double) params.get("age"))
                .ageUnit((AgeUnit) params.get("ageUnit"))
                .weight((Double) params.get("weight"))
                .breed((String) params.get("breed"))
                .createdAt((LocalDate) params.get("createdAt"))
                .build();

        return service.findByCriteria(animalFilter);
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

        AnimalPatchRequest animalPatchRequest = AnimalPatchRequest.builder()
                .name(name)
                .address(addressPatchRequest)
                .age(age)
                .ageUnit(ageUnit)
                .weight(weight)
                .breed(breed)
                .build();

        requestValidator.validateRequest(animalPatchRequest);

        service.update(id, animalPatchRequest);
    }
}
