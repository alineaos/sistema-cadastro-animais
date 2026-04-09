package shelter.animal.dto;

import lombok.Builder;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;

import java.time.LocalDate;

@Builder
public record PetFilter(
        String name,
        PetType type,
        PetSex sex,
        AddressFilter address,
        Double age,
        AgeUnit ageUnit,
        Double weight,
        String breed,
        LocalDate createdAt) {}
