package shelter.animal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;
import shelter.animal.repository.ValidateRepositoryLegacy;
import shelter.animal.repository.converters.AddressConverter;
import shelter.animal.repository.converters.AgeUnitConverter;
import shelter.animal.repository.converters.NumberConverter;
import shelter.animal.repository.converters.PetSexConverter;
import shelter.animal.repository.converters.PetTypeConverter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Pet {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Convert(converter = PetTypeConverter.class)
    @Column(nullable = false)
    private PetType type;

    @Convert(converter = PetSexConverter.class)
    @Column(nullable = false)
    private PetSex sex;

    @Convert(converter = AddressConverter.class)
    @Column(name = "address_info", nullable = false)
    private Address address;

    @Convert(converter = NumberConverter.class)
    @Column(name = "age_value")
    private Double age;

    @Convert(converter = AgeUnitConverter.class)
    @Column(name = "age_unit")
    private AgeUnit ageUnit;

    @Convert(converter = NumberConverter.class)
    @Column
    private Double weight;

    @Column(nullable = false)
    private String breed;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Pet(String name, PetType type, PetSex sex, Address address, Double age, Double weight, String breed) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.address = address;
        this.age = age;
        this.weight = weight;
        this.breed = breed;
    }

    @Override
    public String toString() {
        return "1- " +
                this.getName() +
                "\n2- " +
                this.getType().getClassification() +
                "\n3- " +
                this.getSex().getClassification() +
                "\n4- " +
                this.getAddress() +
                "\n5- " +
                (this.getAge() == null ? ValidateRepositoryLegacy.NAO_INFORMADO : String.format("%.2f anos", this.getAge())) +
                "\n6- " +
                (this.getWeight() == null ? ValidateRepositoryLegacy.NAO_INFORMADO : String.format("%.2fkg", this.getWeight())) +
                "\n7- " +
                this.getBreed();
    }

    public String petFilteredString() {
        return this.getName() +
                ", " +
                this.getType().getClassification() +
                ", " +
                this.getSex().getClassification() +
                ", " +
                this.getAddress() +
                ", " +
                (this.getAge() == null ? ValidateRepositoryLegacy.NAO_INFORMADO : String.format("%.2f anos", this.getAge())) +
                ", " +
                (this.getWeight() == null ? ValidateRepositoryLegacy.NAO_INFORMADO : String.format("%.2fkg", this.getWeight())) +
                ", " +
                this.getBreed();
    }


}
