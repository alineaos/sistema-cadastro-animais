package shelter.animal.dto;

import lombok.Builder;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.AnimalSex;
import shelter.animal.models.enums.AnimalType;

import java.time.LocalDate;

@Builder
public record AnimalFilter(
        String name,
        AnimalType type,
        AnimalSex sex,
        AddressFilter address,
        Integer age,
        AgeUnit ageUnit,
        Double weight,
        String breed,
        LocalDate createdAt
) {}
