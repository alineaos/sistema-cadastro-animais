package shelter.animal.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import shelter.animal.dto.AddressFilter;
import shelter.animal.models.Animal;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.AnimalSex;
import shelter.animal.models.enums.AnimalType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AnimalSpecification {

    public static Specification<Animal> hasName(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(root.get("name"), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Animal> hasType(AnimalType type) {
        return (root, query, cb) ->
                type == null ? null : cb.equal(root.get("type"), type);
    }

    public static Specification<Animal> hasSex(AnimalSex sex) {
        return (root, query, cb) ->
                sex == null ? null : cb.equal(root.get("sex"), sex);
    }

    public static Specification<Animal> hasAddress(AddressFilter address) {
        return (root, query, cb) ->
                address == null ? null : cb.equal(root.get("address"), address);
    }

    public static Specification<Animal> hasAge(Double age, AgeUnit ageUnit) {
        return (root, query, cb) -> {
            if (age == null || ageUnit == null) return null;

            return cb.and(
                    cb.equal(root.get("age"), age),
                    cb.equal(root.get("ageUnit"), ageUnit));
        };
    }

    public static Specification<Animal> hasWeight(Double weight) {
        return (root, query, cb) ->
                weight == null ? null : cb.equal(root.get("weight"), weight);

    }

    public static Specification<Animal> hasBreed(String breed) {
        return (root, query, cb) ->
                breed == null ? null : cb.like(root.get("breed"), "%" + breed.toLowerCase() + "%");
    }

    public static Specification<Animal> hasCreatedAt(LocalDate createdAt){
        return (root, query, cb) ->{
            if (createdAt == null) return null;

            LocalDateTime startOfDay = createdAt.atStartOfDay();
            LocalDateTime endOfDay = createdAt.atTime(LocalTime.MAX);

            return cb.between(root.get("createdAt"), startOfDay, endOfDay);
        };
    }
}
