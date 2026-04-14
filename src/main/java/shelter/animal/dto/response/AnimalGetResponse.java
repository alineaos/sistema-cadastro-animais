package shelter.animal.dto.response;

import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.AnimalSex;
import shelter.animal.models.enums.AnimalType;

import java.time.LocalDateTime;

public record AnimalGetResponse(
        Long id,
        String name,
        AnimalType type,
        AnimalSex sex,
        AddressGetResponse address,
        Double age,
        AgeUnit ageUnit,
        Double weight,
        String breed,
        LocalDateTime createdAt
) {}
