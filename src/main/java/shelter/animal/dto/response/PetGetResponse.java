package shelter.animal.dto.response;

import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;

import java.time.LocalDateTime;

public record PetGetResponse(
        Long id,
        String name,
        PetType type,
        PetSex sex,
        AddressGetResponse address,
        Double age,
        AgeUnit ageUnit,
        Double weight,
        String breed,
        LocalDateTime createdAt
) {}
