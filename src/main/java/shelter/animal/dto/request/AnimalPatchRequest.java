package shelter.animal.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import shelter.animal.models.enums.AgeUnit;

@Builder
public record AnimalPatchRequest(
        @Pattern(regexp = "[a-zA-ZÀ-ÃÉÊÍÓ-ÕÚÇà-ãéêíó-õúç\\s]*"
                , message = "O nome deve conter apenas letras.")
        String name,
        AddressPatchRequest address,
        @Positive(message = "A idade precisa ser um valor maior que 0")
        Double age,
        AgeUnit ageUnit,
        @DecimalMin(value = "0.5", message = "O peso não pode ser menor que 0.5kg")
        @DecimalMax(value = "60.0", message = "O peso não pode ser maior que 60kg")
        Double weight,
        String breed
){
}
