package shelter.animal.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import shelter.animal.dto.AddressFilter;
import shelter.animal.models.Pet;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PetSpecification {

    public static Specification<Pet> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(root.get("name"), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Pet> hasType(PetType type) {
        return (root, query, cb) ->
                type == null ? null : cb.equal(root.get("type"), type);
    }

    public static Specification<Pet> hasSex(PetSex sex) {
        return (root, query, cb) ->
                sex == null ? null : cb.equal(root.get("sex"), sex);
    }

    public static Specification<Pet> hasAddress(AddressFilter address) {
        return (root, query, cb) ->
                address == null ? null : cb.equal(root.get("address"), address);
    }

    public static Specification<Pet> hasAge(Double age, AgeUnit ageUnit) {
        return (root, query, cb) -> {
            if (age == null || ageUnit == null) return null;

            return cb.and(
                    cb.equal(root.get("age"), age),
                    cb.equal(root.get("ageUnit"), ageUnit));
        };
    }

    public static Specification<Pet> hasWeight(Double weight) {
        return (root, query, cb) ->
                weight == null ? null : cb.equal(root.get("weight"), weight);

    }

    public static Specification<Pet> hasBreed(String breed) {
        return (root, query, cb) ->
                breed == null ? null : cb.like(root.get("breed"), "%" + breed.toLowerCase() + "%");
    }

    public static Specification<Pet> hasCreatedAt(LocalDate createdAt){
        return (root, query, cb) ->{
            if (createdAt == null) return null;

            LocalDateTime startOfDay = createdAt.atStartOfDay();
            LocalDateTime endOfDay = createdAt.atTime(LocalTime.MAX);

            return cb.between(root.get("createdAt"), startOfDay, endOfDay);
        };
    }
}
