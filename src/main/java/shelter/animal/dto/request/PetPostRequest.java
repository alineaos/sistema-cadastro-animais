package shelter.animal.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import shelter.animal.models.enums.PetSex;
import shelter.animal.models.enums.PetType;

@Builder
public record PetPostRequest(
        @Pattern(regexp = "[a-zA-ZÀ-ÃÉÊÍÓ-ÕÚÇà-ãéêíó-õúç\\s]+"
                , message = "O nome deve conter apenas letras.")
        String name,
        @NotNull(message = "O tipo do animal é obrigatório")
        PetType type,
        @NotNull(message = "O sexo do animal é obrigatório")
        PetSex sex,
        @NotNull(message = "O endereço é obrigatório")
        AddressPostRequest address,
        Double age,
        @DecimalMin(value = "0.5", message = "O peso não pode ser menor que 0.5kg")
        @DecimalMax(value = "60.0", message = "O peso não pode ser maior que 60kg")
        Double weight,
        String breed
) {}
