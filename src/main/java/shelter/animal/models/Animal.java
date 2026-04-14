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
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import shelter.animal.models.enums.AgeUnit;
import shelter.animal.models.enums.AnimalSex;
import shelter.animal.models.enums.AnimalType;
import shelter.animal.repository.converters.AddressConverter;
import shelter.animal.repository.converters.AgeUnitConverter;
import shelter.animal.repository.converters.WeightConverter;
import shelter.animal.repository.converters.AnimalSexConverter;
import shelter.animal.repository.converters.AnimalTypeConverter;
import shelter.animal.repository.converters.AgeConverter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Animal {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Convert(converter = AnimalTypeConverter.class)
    @Column(nullable = false)
    private AnimalType type;

    @Convert(converter = AnimalSexConverter.class)
    @Column(nullable = false)
    private AnimalSex sex;

    @Convert(converter = AddressConverter.class)
    @Column(name = "address_info", nullable = false)
    private Address address;

    @Convert(converter = AgeConverter.class)
    @Column(name = "age_value")
    private Integer age;

    @Convert(converter = AgeUnitConverter.class)
    @Column(name = "age_unit")
    private AgeUnit ageUnit;

    @Convert(converter = WeightConverter.class)
    @Column
    private Double weight;

    @Column(nullable = false)
    private String breed;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
